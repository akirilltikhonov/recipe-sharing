package net.azeti.challenge.application.infra.security.user;

import java.util.Optional;

public interface UserManagement {

    RegistrationResult register(Registration registration);

    Optional<Token> login(Login login);
}
