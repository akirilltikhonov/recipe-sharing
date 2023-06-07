package net.azeti.challenge.application.app.port.repository;

import net.azeti.challenge.application.domain.User;
import net.azeti.challenge.application.domain.authentification.Registration;

import java.util.Optional;

public interface UserRepository {

    User registrate(Registration registration);

    Optional<User> findByUsername(String username);
}
