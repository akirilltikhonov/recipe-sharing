package net.azeti.challenge.application.app.port.repository;

import net.azeti.challenge.application.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);
}
