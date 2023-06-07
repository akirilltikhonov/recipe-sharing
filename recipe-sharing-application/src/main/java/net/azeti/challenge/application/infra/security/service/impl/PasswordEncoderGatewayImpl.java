package net.azeti.challenge.application.infra.security.service.impl;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.application.app.port.gateway.PasswordEncoderGateway;
import net.azeti.challenge.application.app.port.gateway.mapping.EncodePasswordEncoderAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncoderGatewayImpl implements PasswordEncoderGateway {

    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    @EncodePasswordEncoderAdapter
    public String encode(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }
}
