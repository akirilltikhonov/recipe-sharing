package net.azeti.challenge.application.infra.jpa.repository.impl;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.application.app.port.repository.UserRepository;
import net.azeti.challenge.application.domain.User;
import net.azeti.challenge.application.domain.authentification.Registration;
import net.azeti.challenge.application.domain.exception.user.UserExistException;
import net.azeti.challenge.application.infra.jpa.mapper.RegistrationMapper;
import net.azeti.challenge.application.infra.jpa.mapper.UserMapper;
import net.azeti.challenge.application.infra.jpa.repository.UserJpaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final RegistrationMapper registrationMapper;
    private final UserMapper userMapper;

    @Override
    public User registrate(Registration registration) {
        try {
            var userEntity = registrationMapper.toUserEntity(registration);
            return userMapper.toUser(userJpaRepository.save(userEntity));
        } catch (DataIntegrityViolationException e) {
            throw new UserExistException(registration.getUsername(), registration.getEmail());
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(userMapper::toUser);
    }
}