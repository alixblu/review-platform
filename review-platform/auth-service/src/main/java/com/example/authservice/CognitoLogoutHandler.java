package com.example.authservice;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
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
        String logoutUrl = UriComponentsBuilder
                .fromUri(URI.create(domain + "/logout"))
                .queryParam("client_id", userPoolClientId)
                .queryParam("logout_uri", logoutRedirectUrl)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();

        response.sendRedirect(logoutUrl);
    }
}