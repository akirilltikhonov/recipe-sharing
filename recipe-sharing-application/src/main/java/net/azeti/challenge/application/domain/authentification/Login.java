package net.azeti.challenge.application.domain.authentification;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Login {
    String username;
    String password;
}
