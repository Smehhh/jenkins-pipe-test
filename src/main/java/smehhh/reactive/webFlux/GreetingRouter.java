package smehhh.reactive.webFlux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import smehhh.reactive.webFlux.handler.GreetingHandler;

import java.util.Map;

@Configuration
public class GreetingRouter {

    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/hello")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::hello)
                .andRoute(
                        RequestPredicates.GET("/"),
                        handler::index
                );
    }
}
