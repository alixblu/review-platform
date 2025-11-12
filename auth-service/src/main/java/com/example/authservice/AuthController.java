package com.example.authservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    @Value("${cognito.domain}")
    private String cognitoDomain;

    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.cognito.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/exchange")
    public ResponseEntity<?> exchangeAuthCodeForTokens(@RequestBody Map<String, String> payload) {
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
                new ParameterizedTypeReference<Map<String, Object>>() {});
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
                new ParameterizedTypeReference<Map<String, Object>>() {});

        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("idToken", tokenResponse.get("id_token"));
        result.put("refreshToken", tokenResponse.get("refresh_token"));
        result.put("expiresIn", tokenResponse.get("expires_in"));
        result.put("tokenType", tokenResponse.get("token_type"));
        result.put("user", userInfoResp.getBody());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String authorization,
                                org.springframework.security.core.Authentication authentication) {
        // If Bearer token is provided, use it (token-based flow)
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
                    new ParameterizedTypeReference<Map<String, Object>>() {});
            return ResponseEntity.status(userInfoResp.getStatusCode()).body(userInfoResp.getBody());
        }

        // Else, use session-based authentication (backend-managed login)
        if (authentication != null && authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.OAuth2User principal) {
            Map<String, Object> attrs = principal.getAttributes();
            return ResponseEntity.ok(attrs);
        }

        Map<String, Object> error = new HashMap<>();
        error.put("error", "unauthorized");
        return ResponseEntity.status(401).body(error);
    }
}


