package net.azeti.challenge.application.infra.security.service.impl;

import net.azeti.challenge.application.infra.security.service.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TokenProviderGatewayImplTest {

    @InjectMocks
    private TokenProviderGatewayImpl tokenProviderGateway;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void getUsername() {
        String accessToken = "token";
        String username = "username";
        doReturn(username).when(jwtTokenProvider).getUsername(accessToken);

        assertThat(tokenProviderGateway.getUsername(accessToken))
                .isEqualTo(username);
    }
}