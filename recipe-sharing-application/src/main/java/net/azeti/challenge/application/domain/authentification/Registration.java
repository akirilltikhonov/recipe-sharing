package net.azeti.challenge.application.domain.authentification;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Registration {
    String username;
    String password;
    String email;
}
