package net.azeti.challenge.application.infra.security.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class PasswordEncoderGatewayImplTest {

    @InjectMocks
    private PasswordEncoderGatewayImpl passwordEncoderGateway;
    @Mock
    private PasswordEncoder bCryptPasswordEncoder;

    @Test
    void encode() {
        String rawPassword = "rawPassword";

        String encodedPassword = "username";
        doReturn(encodedPassword).when(bCryptPasswordEncoder).encode(rawPassword);

        assertThat(passwordEncoderGateway.encode(rawPassword))
                .isEqualTo(encodedPassword);
    }
}