package smehhh.reactive.webFlux.config;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthManager implements ReactiveAuthenticationManager{
    private final JwtUtil jwtUtil;

    public AuthManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        try {
            String username = jwtUtil.extractUsername(authToken);
            jwtUtil.validateToken(authToken);
            List<String> roles = jwtUtil.getClaimsFromToken(authToken).get("role", List.class);
            return Mono.just(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            roles.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList())
                    )
            );
        } catch (Exception e) {
            System.out.println(e);
        }
        return Mono.empty();
    }
}
