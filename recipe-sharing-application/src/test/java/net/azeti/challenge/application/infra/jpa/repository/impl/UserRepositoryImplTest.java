package net.azeti.challenge.application.infra.jpa.repository.impl;

import net.azeti.challenge.application.domain.User;
import net.azeti.challenge.application.domain.authentification.Registration;
import net.azeti.challenge.application.infra.jpa.entity.UserEntity;
import net.azeti.challenge.application.infra.jpa.mapper.UserMapper;
import net.azeti.challenge.application.infra.jpa.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @InjectMocks
    private UserRepositoryImpl userRepository;
    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private UserMapper userMapper;

    @Test
    void registrate() {
        var registration = Registration.builder().build();

        var userEntity = new UserEntity();
        doReturn(userEntity).when(userMapper).toUserEntity(registration);
        doReturn(userEntity).when(userJpaRepository).save(userEntity);
        var user = User.builder().build();
        doReturn(user).when(userMapper).toUser(userEntity);

        assertThat(userRepository.registrate(registration)).isEqualTo(user);
    }

    @Test
    void findByUsername() {
        String username = "username";

        var userEntity = new UserEntity();
        doReturn(Optional.of(userEntity)).when(userJpaRepository).findByUsername(username);
        var user = User.builder()
                .username(username)
                .build();
        doReturn(user).when(userMapper).toUser(userEntity);

        assertThat(userRepository.findByUsername(username)).isEqualTo(Optional.of(user));
    }
}