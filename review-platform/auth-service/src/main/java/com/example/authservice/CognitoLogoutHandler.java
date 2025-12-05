package com.example.authservice;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Cognito has a custom logout url.
 * See more information <a href=
 * "https://docs.aws.amazon.com/cognito/latest/developerguide/logout-endpoint.html">here</a>.
 */
public class CognitoLogoutHandler extends SimpleUrlLogoutSuccessHandler {

    /**
     * The domain of your user pool.
     */
    private final String domain;

    /**
     * An allowed callback URL.
     */
    private final String logoutRedirectUrl;

    /**
     * The ID of your User Pool Client.
     */
    private final String userPoolClientId;

    public CognitoLogoutHandler(String domain, String logoutRedirectUrl, String userPoolClientId) {
        this.domain = domain;
        this.logoutRedirectUrl = logoutRedirectUrl;
        this.userPoolClientId = userPoolClientId;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // Ensure the logout URL is properly constructed with URL encoding
        // The logout_uri must match exactly one of the Allowed Sign-Out URLs in Cognito
        // Important: The logout_uri must be registered in Cognito App Client Settings > Sign out URL(s)
        String encodedLogoutUri = URLEncoder.encode(logoutRedirectUrl, StandardCharsets.UTF_8);
        String logoutUrl = String.format("%s/logout?client_id=%s&logout_uri=%s",
                domain,
                userPoolClientId,
                encodedLogoutUri);

        response.sendRedirect(logoutUrl);
    }
}