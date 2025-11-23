//package com.example.apigateway;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Component
//public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
//
//    @Value("${jwt.public-paths:/api/auth/exchange,/api/auth/refresh,/logout}")
//    private String publicPaths;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        String path = request.getURI().getPath();
//
//        // Skip authentication for public paths
//        if (isPublicPath(path)) {
//            return chain.filter(exchange);
//        }
//
//        // Check for Authorization header
//        List<String> authHeaders = request.getHeaders().get("Authorization");
//
//        if (authHeaders == null || authHeaders.isEmpty()) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        String authHeader = authHeaders.get(0);
//
//        if (!authHeader.startsWith("Bearer ")) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        // Token exists, let it pass through
//        // The actual JWT validation will be done by the downstream services
//        return chain.filter(exchange);
//    }
//
//    private boolean isPublicPath(String path) {
//        String[] publicPathArray = publicPaths.split(",");
//        for (String publicPath : publicPathArray) {
//            if (path.startsWith(publicPath.trim())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public int getOrder() {
//        return -100; // Run before other filters
//    }
//}
