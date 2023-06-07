package net.azeti.challenge.application.infra.security.service.impl;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.application.app.port.gateway.AuthenticationGateway;
import net.azeti.challenge.application.domain.authentification.Token;
import net.azeti.challenge.application.infra.security.model.exception.JwtAuthenticationException;
import net.azeti.challenge.application.infra.security.service.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
        try {
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
        } catch (AuthenticationException e) {
            throw new JwtAuthenticationException("Invalid username/password combination", HttpStatus.FORBIDDEN);
        }
    }
}
