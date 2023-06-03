package net.azeti.challenge.application.infra.security.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Token {
    private String accessToken;
}
