package smehhh.reactive.webFlux.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import smehhh.reactive.webFlux.domain.Message;
import smehhh.reactive.webFlux.service.MessageService;

import java.util.Map;


@Component
public class GreetingHandler {

    private final MessageService messageService;

    public GreetingHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    public Mono<ServerResponse> hello(ServerRequest serverRequest) {
        Long start = serverRequest.queryParam("start").map(Long::valueOf).orElse(0L);
        Long count = serverRequest.queryParam("count").map(Long::valueOf).orElse(3L);

        Flux<Message> messages = messageService.getMessages();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(messages, Message.class);
    }

    public Mono<ServerResponse> index(ServerRequest serverRequest) {
        String name = serverRequest.queryParam("user").orElse("Nobody");
        return ServerResponse
                .ok()
                .render("index", Map.of("user", name));
    }

}
