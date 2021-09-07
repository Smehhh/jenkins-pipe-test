package smehhh.reactive.webFlux;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import smehhh.reactive.webFlux.config.JwtUtil;
import smehhh.reactive.webFlux.domain.User;
import smehhh.reactive.webFlux.service.UserService;

import java.util.Objects;

@RestController
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final static ResponseEntity<Objects> UNAUTH = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity> login(ServerWebExchange exchange) {

        return exchange.getFormData()
                .flatMap(cred ->
                        userService.findByUsername(cred.getFirst("username"))
                                .cast(User.class)
                                .map(user ->
                                        Objects.equals(
                                                cred.getFirst("password"),
                                                user.getPassword()
                                        )
                                                ? ResponseEntity.ok(jwtUtil.genToken(user))
                                                : UNAUTH).defaultIfEmpty(UNAUTH));
    }
}
