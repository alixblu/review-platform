package com.example.authservice;

import com.example.authservice.httpclient.UserClient;
import com.example.authservice.httpclient.UserCreationRequest;
import com.example.commonlib.dto.ApiResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${cognito.domain}")
    private String cognitoDomain;

    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.cognito.client-secret}")
    private String clientSecret;

    private final UserClient userClient;

    private final RestTemplate restTemplate = new RestTemplate();

    private void ensureUserExists(Map<String, Object> userInfo) {
        if (userInfo == null || userInfo.get("sub") == null) {
            log.warn("ensureUserExists called with missing userInfo/sub");
            return;
        }
        String cognitoUserId = userInfo.get("sub").toString();
        try {
            // Try to get user by Cognito accId
            userClient.getUserByAccId(cognitoUserId);
            log.info("User already exists: {}", cognitoUserId);
        } catch (FeignException.NotFound e) {
            // User doesn't exist, create new user
            log.info("User not found, creating new user: {}", cognitoUserId);
            try {
                UUID accUuid = UUID.fromString(cognitoUserId);
                UserCreationRequest createRequest = UserCreationRequest.builder()
                        .accId(accUuid)
                        .name(userInfo.getOrDefault("name", userInfo.getOrDefault("username", "User")).toString())
                        .profilePic(userInfo.getOrDefault("picture", null) != null ?
                                userInfo.get("picture").toString() : null)
                        .build();
                userClient.createUser(createRequest);
                log.info("User created successfully: {}", cognitoUserId);
            } catch (IllegalArgumentException uuidEx) {
                log.error("Cognito sub is not a valid UUID: {}", cognitoUserId);
            } catch (Exception ce) {
                log.error("Error creating user in user-service: {}", ce.getMessage());
            }
        } catch (FeignException e) {
            log.error("Error calling user-service (status {}): {}", e.status(), e.getMessage());
        } catch (Exception e) {
            log.error("Error checking/creating user: {}", e.getMessage());
        }
    }

    public ResponseEntity<?> exchangeAuthCodeForTokens(Map<String, String> payload) {
        String code = payload.get("code");
        String redirectUri = payload.get("redirectUri");
        if (code == null || redirectUri == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "invalid_request");
            error.put("error_description", "code and redirectUri are required");
            return ResponseEntity.badRequest().body(error);
        }

        String tokenUrl = cognitoDomain + "/oauth2/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "authorization_code");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("code", code);
        form.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        ResponseEntity<Map<String, Object>> tokenResp = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String, Object>>() {
                });
        Map<String, Object> tokenResponse = tokenResp.getBody();

        if (tokenResponse == null || tokenResponse.get("access_token") == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "invalid_token_response");
            return ResponseEntity.status(502).body(error);
        }

        String accessToken = tokenResponse.get("access_token").toString();
        String userInfoUrl = cognitoDomain + "/oauth2/userInfo";

        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.setBearerAuth(accessToken);
        HttpEntity<Void> userInfoReq = new HttpEntity<>(userInfoHeaders);
        ResponseEntity<Map<String, Object>> userInfoResp = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.POST,
                userInfoReq,
                new ParameterizedTypeReference<Map<String, Object>>() {
                });

        Map<String, Object> userInfo = userInfoResp.getBody();

        // Ensure user exists in user-service
        ensureUserExists(userInfo);

        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("idToken", tokenResponse.get("id_token"));
        result.put("refreshToken", tokenResponse.get("refresh_token"));
        result.put("expiresIn", tokenResponse.get("expires_in"));
        result.put("tokenType", tokenResponse.get("token_type"));
        result.put("user", userInfo);

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<ApiResponse<?>> me(String authorization,
                                             org.springframework.security.core.Authentication authentication) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring("Bearer ".length());

            String userInfoUrl = cognitoDomain + "/oauth2/userInfo";
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<Void> req = new HttpEntity<>(headers);
            ResponseEntity<Map<String, Object>> userInfoResp = restTemplate.exchange(
                    userInfoUrl,
                    HttpMethod.POST,
                    req,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            Map<String, Object> userInfo = userInfoResp.getBody();
            if (userInfo != null) {
                Object groups = userInfo.get("cognito:groups");
                boolean isAdmin = false;

                if (groups instanceof List<?> groupList) {
                    isAdmin = groupList.contains("Admin");
                } else if (groups instanceof String groupStr) {
                    isAdmin = groupStr.contains("Admin");
                }

                String username = (String) userInfo.getOrDefault("username", "unknown");
                if (isAdmin) {
                    log.info("🛡️ Admin user logged in via token: {}", username);
                } else {
                    log.info("👤 Regular user logged in via token: {}", username);
                }

                // Ensure user exists in user-service
                ensureUserExists(userInfo);
            }

            return ResponseEntity.status(userInfoResp.getStatusCode())
                    .body(new ApiResponse<>("(token) login successfully", userInfo));
        }

        if (authentication != null &&
                authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.OAuth2User principal) {
            Map<String, Object> attrs = principal.getAttributes();
            Object groups = attrs.get("cognito:groups");
            boolean isAdmin = false;

            if (groups instanceof List<?> groupList) {
                isAdmin = groupList.contains("Admin");
            } else if (groups instanceof String groupStr) {
                isAdmin = groupStr.contains("Admin");
            }

            String username = (String) attrs.getOrDefault("username", "unknown");
            if (isAdmin) {
                log.info("🛡️ Admin user logged in via session: {}", username);
            } else {
                log.info("👤 Regular user logged in via session: {}", username);
            }

            // Ensure user exists in user-service
            ensureUserExists(attrs);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("(session-based) login successfully", attrs));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>("login failed, user unauthorize", null));
    }
}




