package com.ecom.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilterFactory.Config> {

    private final GatewayJwtUtil jwtUtil;

    public JwtAuthenticationGatewayFilterFactory(GatewayJwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {


            String path = exchange.getRequest().getURI().getPath();


            //  Skip JWT check for auth endpoints
            if (path.startsWith("/api/v1/auth")) {
                return chain.filter(exchange);
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);

            if (!jwtUtil.validateToken(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // Optional: Pass user info to downstream microservices
            String username = jwtUtil.extractUsername(token);
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("X-auth-user", username)
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        };
    }

    public static class Config {
        // Add fields here if you want to pass config from YAML
    }
}
