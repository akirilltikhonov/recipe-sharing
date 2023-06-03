package net.azeti.challenge.recipe.user;

import java.util.Optional;

public interface UserManagement {

    RegistrationResult register(Registration registration);

    Optional<Token> login(Login login);
}
