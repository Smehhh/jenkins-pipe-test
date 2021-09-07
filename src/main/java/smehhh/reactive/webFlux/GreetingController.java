package smehhh.reactive.webFlux;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import smehhh.reactive.webFlux.domain.Message;
import smehhh.reactive.webFlux.service.MessageService;

@RestController
@RequestMapping("/controller")
public class GreetingController {

    private final MessageService messageService;

    public GreetingController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public Flux<Message> list(@RequestParam(defaultValue = "0")Long start, @RequestParam(defaultValue = "4")Long count) {
        return messageService.getMessages();
    }

    @PostMapping("/add")
    public Mono<Message> addMessage(@RequestBody Message message) {
        System.out.println(message);
        return messageService.addMessage(message);
    }
}
