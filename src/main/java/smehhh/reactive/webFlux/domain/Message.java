package smehhh.reactive.webFlux.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Message {
    public String message;
    @Id
    public Long id;

    public Message(String message) {
        this.message = message;
    }
}
