package com.example.authservice;

import com.example.authservice.httpclient.UserClient;
import com.example.authservice.httpclient.UserCreationRequest;
import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${cognito.domain}")
    private String cognitoDomain;

    @Value("${auth.redirect-uri}")
    private String oauth2RedirectUri;

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

    public Map<?, ?> exchangeAuthCodeForTokens(Map<String, String> payload) {
        String code = payload.get("code");
        if (code == null)
            throw new AppException(ErrorCode.INVALID_REQUEST, "code is required");

        String tokenUrl = cognitoDomain + "/oauth2/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "authorization_code");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("code", code);
        form.add("redirect_uri", oauth2RedirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        ResponseEntity<Map<String, Object>> tokenResp;
        try {
            tokenResp = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });
        } catch (org.springframework.web.client.RestClientResponseException ex) {
            throw new AppException(ErrorCode.INVALID_REQUEST, "Token exchange failed: " + ex.getStatusCode().value() + " " + ex.getResponseBodyAsString());
        }
        Map<String, Object> tokenResponse = tokenResp.getBody();

        if (tokenResponse == null || tokenResponse.get("access_token") == null)
            throw new AppException(ErrorCode.INVALID_TOKEN_RESPONSE);

        String accessToken = tokenResponse.get("access_token").toString();
        String userInfoUrl = cognitoDomain + "/oauth2/userInfo";
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.setBearerAuth(accessToken);
        HttpEntity<Void> userInfoReq = new HttpEntity<>(userInfoHeaders);
        ResponseEntity<Map<String, Object>> userInfoResp;
        try {
            userInfoResp = restTemplate.exchange(
                    userInfoUrl,
                    HttpMethod.GET,
                    userInfoReq,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });
        } catch (org.springframework.web.client.RestClientResponseException ex) {
            throw new AppException(ErrorCode.INVALID_REQUEST, "UserInfo failed: " + ex.getStatusCode().value() + " " + ex.getResponseBodyAsString());
        }

        Map<String, Object> userInfo = userInfoResp.getBody();

        // Ensure user exists in user-service
        ensureUserExists(userInfo);

        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("idToken", tokenResponse.get("id_token"));
        result.put("refreshToken", tokenResponse.get("refresh_token"));
        result.put("expiresIn", tokenResponse.get("expires_in"));
        result.put("tokenType", tokenResponse.get("token_type"));
        result.put("account", userInfo);

        return result;
    }

    public Map<?, ?> refreshToken(Map<String, String> payload) {
        String refreshToken = payload.get("refreshToken");
        if (refreshToken == null)
            throw new AppException(ErrorCode.INVALID_REQUEST, "refreshToken is required");

        String tokenUrl = cognitoDomain + "/oauth2/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "refresh_token");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        ResponseEntity<Map<String, Object>> tokenResp;
        try {
            tokenResp = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });
        } catch (org.springframework.web.client.RestClientResponseException ex) {
            throw new AppException(ErrorCode.INVALID_REQUEST,
                    "Token refresh failed: " + ex.getStatusCode().value() + " " + ex.getResponseBodyAsString());
        }
        Map<String, Object> tokenResponse = tokenResp.getBody();

        if (tokenResponse == null || tokenResponse.get("access_token") == null)
            throw new AppException(ErrorCode.INVALID_TOKEN_RESPONSE);

        String accessToken = tokenResponse.get("access_token").toString();

        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("idToken", tokenResponse.get("id_token"));
        result.put("expiresIn", tokenResponse.get("expires_in"));
        result.put("tokenType", tokenResponse.get("token_type"));

        log.info("🔄 Token refreshed successfully");
        return result;
    }

}
