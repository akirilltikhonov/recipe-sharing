package net.azeti.challenge.application.domain.authentification;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Token {
    String accessToken;
}
