package net.azeti.challenge.application.infra.security.service.impl;

import net.azeti.challenge.application.domain.authentification.Token;
import net.azeti.challenge.application.infra.security.service.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticationGatewayImplTest {

    @InjectMocks
    private AuthenticationGatewayImpl authenticationService;

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void authenticate() {
        String username = "username";
        String password = "password";

        Authentication authentication = mock(Authentication.class);
        doReturn(Set.of()).when(authentication).getAuthorities();
        var authToken = new UsernamePasswordAuthenticationToken(username, password);
        doReturn(authentication).when(authenticationManager).authenticate(authToken);

        String accessToken = "token";
        doReturn(accessToken).when(jwtTokenProvider).createToken(username, Set.of());

        Token token = Token.builder()
                .accessToken(accessToken)
                .build();
        assertThat(authenticationService.authenticate(username, password))
                .isEqualTo(token);

        verify(authenticationManager).authenticate(authToken);
        verify(jwtTokenProvider).createToken(username, Set.of());
    }
}