package net.azeti.challenge.application.integrationtest.layer.jpa.test.repository.impl;

import net.azeti.challenge.application.app.port.repository.UserRepository;
import net.azeti.challenge.application.domain.authentification.Registration;
import net.azeti.challenge.application.domain.exception.user.UserExistException;
import net.azeti.challenge.application.integrationtest.layer.jpa.ApplicationJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserRepositoryTest extends ApplicationJpaTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void registrateTwoSameUsers() {
        var registration = Registration.builder()
                .username(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString())
                .email(UUID.randomUUID() + "@mail.com")
                .build();

        assertThat(userRepository.registrate(registration))
                .usingRecursiveComparison()
                .isEqualTo(registration);

        assertThatThrownBy(() -> userRepository.registrate(registration))
                .isInstanceOf(UserExistException.class);
    }
}
