package net.azeti.challenge.application.infra.security.user;

import net.azeti.challenge.application.domain.authentification.Login;
import net.azeti.challenge.application.domain.authentification.Token;

import java.util.Optional;

public interface UserManagement {

    RegistrationResult register(Registration registration);

    Optional<Token> login(Login login);
}
