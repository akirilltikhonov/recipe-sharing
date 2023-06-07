package net.azeti.challenge.application.app.port.gateway;

import net.azeti.challenge.application.app.port.gateway.mapping.GetUsernameTokenProviderGateway;

public interface TokenProviderGateway {

    @GetUsernameTokenProviderGateway
    String getUsername(String accessToken);
}
