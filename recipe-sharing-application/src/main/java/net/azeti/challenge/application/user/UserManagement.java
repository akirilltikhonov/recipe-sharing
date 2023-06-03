package net.azeti.challenge.application.user;

import java.util.Optional;

public interface UserManagement {

    RegistrationResult register(Registration registration);

    Optional<Token> login(Login login);
}
