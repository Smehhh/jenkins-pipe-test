package smehhh.reactive.webFlux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import smehhh.reactive.webFlux.domain.Message;

public interface MessageRepo extends ReactiveCrudRepository<Message, Long> {

}
