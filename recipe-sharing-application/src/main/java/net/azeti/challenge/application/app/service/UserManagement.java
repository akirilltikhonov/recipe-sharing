package net.azeti.challenge.application.app.service;

import net.azeti.challenge.application.domain.authentification.Login;
import net.azeti.challenge.application.domain.authentification.Registration;
import net.azeti.challenge.application.domain.authentification.RegistrationResult;
import net.azeti.challenge.application.domain.authentification.Token;

public interface UserManagement {

    RegistrationResult register(Registration registration);

    Token login(Login login);
}
