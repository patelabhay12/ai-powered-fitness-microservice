package com.fitness.gateway;

import com.fitness.gateway.user.RegisterRequest;
import com.fitness.gateway.user.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeyClockUserSyncFilter implements WebFilter {

    private final UserService userService;

    @Override
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        assert token != null;
        RegisterRequest registerRequest = getUserDetails(token);
        if (userId == null) {
            userId = registerRequest.getKeyCloakId();
        }

        if (userId != null) {
            String finalUserId = userId;
            return userService.validateUser(userId).flatMap(exist -> {
                if (!exist) {
                    return userService.registerUser(registerRequest).then(Mono.empty());
                } else {
                    log.info("User already exist, Skipping sync");
                    return Mono.empty();
                }
            }).then(Mono.defer(() -> {
                ServerHttpRequest mutateRequest = (ServerHttpRequest) exchange.getRequest().mutate().header("X-User-ID", finalUserId).build();
                return chain.filter(exchange.mutate().request((Consumer<org.springframework.http.server.reactive.ServerHttpRequest.Builder>) mutateRequest).build());
            }));
        }
        return chain.filter(exchange);
    }

    private RegisterRequest getUserDetails(String token) {
        try {
            String tokenWithoutBearer = token.replace("Bearer", "").trim();

            SignedJWT signedJWT = SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();


            RegisterRequest newRegisterRequest = new RegisterRequest();
            newRegisterRequest.setEmail(claims.getStringClaim("email"));
            newRegisterRequest.setKeyCloakId(claims.getStringClaim("sub"));
            newRegisterRequest.setFirstName(claims.getStringClaim("given_name"));
            newRegisterRequest.setLastName(claims.getStringClaim("family_name"));
            newRegisterRequest.setPassword("testadmin123@");

            return newRegisterRequest;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
