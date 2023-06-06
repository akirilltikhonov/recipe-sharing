package net.azeti.challenge.application.app.port.gateway;

import net.azeti.challenge.application.domain.authentification.Token;

public interface AuthenticationGateway {

    Token authenticate(String username, String password);

}
