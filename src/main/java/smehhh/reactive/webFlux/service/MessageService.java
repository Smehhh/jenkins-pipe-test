package smehhh.reactive.webFlux.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import smehhh.reactive.webFlux.domain.Message;
import smehhh.reactive.webFlux.repository.MessageRepo;

@Service
public class MessageService {

    private final MessageRepo messageRepo;

    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public Flux<Message> getMessages() {
        return messageRepo.findAll();
    }

    public Mono<Message> addMessage(Message message) {
        return messageRepo.save(message);
    }

}
