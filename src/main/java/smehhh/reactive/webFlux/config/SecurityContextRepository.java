package smehhh.reactive.webFlux.config;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final AuthManager authManager;

    public SecurityContextRepository(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new IllegalStateException("Save method not supported");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            String authtoken = authHeader.substring(7);
            return authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authtoken, authtoken
            )).map(SecurityContextImpl::new);
        }
        return Mono.empty();
    }
}
