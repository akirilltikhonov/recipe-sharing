package net.azeti.challenge.application.infra.security.service.impl;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.application.app.port.gateway.AuthenticationGateway;
import net.azeti.challenge.application.domain.authentification.Token;
import net.azeti.challenge.application.infra.security.service.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationGatewayImpl implements AuthenticationGateway {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Token authenticate(String username, String password) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String accessToken = jwtTokenProvider.createToken(
                username,
                authenticate.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet())
        );
        return Token.builder()
                .accessToken(accessToken)
                .build();
    }
}
