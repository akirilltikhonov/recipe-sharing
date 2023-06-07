package net.azeti.challenge.application.infra.security.service.impl;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.application.app.port.gateway.TokenProviderGateway;
import net.azeti.challenge.application.app.port.gateway.mapping.GetUsernameTokenProviderGateway;
import net.azeti.challenge.application.infra.security.service.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenProviderGatewayImpl implements TokenProviderGateway {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @GetUsernameTokenProviderGateway
    public String getUsername(String accessToken) {
        return jwtTokenProvider.getUsername(accessToken);
    }
}
