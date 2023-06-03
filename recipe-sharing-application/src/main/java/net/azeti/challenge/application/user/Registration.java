package net.azeti.challenge.application.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Registration {
    private String email;
    private String username;
    private String password;
}