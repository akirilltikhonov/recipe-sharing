package net.azeti.challenge.application.app.service.impl;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.application.app.port.gateway.AuthenticationGateway;
import net.azeti.challenge.application.app.service.UserManagement;
import net.azeti.challenge.application.domain.authentification.Login;
import net.azeti.challenge.application.domain.authentification.Token;
import net.azeti.challenge.application.infra.security.user.Registration;
import net.azeti.challenge.application.infra.security.user.RegistrationResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagementImpl implements UserManagement {

    private final AuthenticationGateway authenticationGateway;

    @Override
    public RegistrationResult register(Registration registration) {
        return null;
    }

    @Override
    public Token login(Login login) {
        return authenticationGateway.authenticate(login.getUsername(), login.getPassword());
    }
}
