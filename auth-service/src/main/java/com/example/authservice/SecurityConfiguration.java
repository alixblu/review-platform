package com.example.authservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public CognitoLogoutHandler cognitoLogoutHandler(
            @Value("${cognito.domain}") String domain,
            @Value("${cognito.logoutRedirect}") String logoutRedirect,
            @Value("${spring.security.oauth2.client.registration.cognito.client-id}") String clientId
    ) {
        return new CognitoLogoutHandler(domain, logoutRedirect, clientId);
    }

    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http, CognitoLogoutHandler cognitoLogoutHandler) throws Exception {

		RegexRequestMatcher logoutGetMatcher = new RegexRequestMatcher("^/logout$", "GET");

        http
                .csrf(csrf -> csrf.disable()) // Optional: disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/oauth2/**", "/login/**", "/api/auth/exchange", "/logout").permitAll()
                        .requestMatchers("/admin/**").hasRole("admin")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .successHandler((request, response, authentication) -> response.sendRedirect("http://localhost:3000/auth/callback"))
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .logout(logout -> logout
						.logoutRequestMatcher(logoutGetMatcher)
                        .permitAll()
                        .logoutSuccessHandler(cognitoLogoutHandler)
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String jwkSetUri = "https://cognito-idp.us-east-1.amazonaws.com/us-east-1_jHi5WpbS9/.well-known/jwks.json";
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> groups = jwt.getClaimAsStringList("cognito:groups");
            if (groups == null || groups.isEmpty()) return Collections.emptyList();
//            if (groups == null || groups.isEmpty()) {
//                return List.of(new SimpleGrantedAuthority("ROLE_User"));
//            }
//
            return groups.stream()
                    .map(group -> new SimpleGrantedAuthority("ROLE_" + group))
                    .collect(Collectors.toList());
        });
        return converter;
    }
}