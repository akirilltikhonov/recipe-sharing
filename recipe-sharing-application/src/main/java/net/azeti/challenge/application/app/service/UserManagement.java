package net.azeti.challenge.application.app.service;

import net.azeti.challenge.application.domain.authentification.Login;
import net.azeti.challenge.application.domain.authentification.Token;
import net.azeti.challenge.application.infra.security.user.Registration;
import net.azeti.challenge.application.infra.security.user.RegistrationResult;

public interface UserManagement {

    RegistrationResult register(Registration registration);

    Token login(Login login);
}
