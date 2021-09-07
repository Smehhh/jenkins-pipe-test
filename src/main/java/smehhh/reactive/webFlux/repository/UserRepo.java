package smehhh.reactive.webFlux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import smehhh.reactive.webFlux.domain.User;

public interface UserRepo extends ReactiveCrudRepository<User, Long> {

    Mono<User> findByUsername(String name);
}
