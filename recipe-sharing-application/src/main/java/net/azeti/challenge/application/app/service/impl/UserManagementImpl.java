package net.azeti.challenge.application.app.service.impl;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.application.app.port.gateway.AuthenticationGateway;
import net.azeti.challenge.application.app.port.repository.UserRepository;
import net.azeti.challenge.application.app.service.UserManagement;
import net.azeti.challenge.application.domain.User;
import net.azeti.challenge.application.domain.authentification.Login;
import net.azeti.challenge.application.domain.authentification.Registration;
import net.azeti.challenge.application.domain.authentification.RegistrationResult;
import net.azeti.challenge.application.domain.authentification.Token;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagementImpl implements UserManagement {

    private final UserRepository userRepository;
    private final AuthenticationGateway authenticationGateway;

    @Override
    public RegistrationResult register(Registration registration) {
        User user = userRepository.registrate(registration);
        return RegistrationResult.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Override
    public Token login(Login login) {
        return authenticationGateway.authenticate(login.getUsername(), login.getPassword());
    }
}
