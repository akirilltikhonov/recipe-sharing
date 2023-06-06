package net.azeti.challenge.application.infra.jpa.repository.impl;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.application.app.port.repository.UserRepository;
import net.azeti.challenge.application.domain.User;
import net.azeti.challenge.application.infra.jpa.mapper.UserMapper;
import net.azeti.challenge.application.infra.jpa.repository.UserJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(userMapper::toUser);
    }
}