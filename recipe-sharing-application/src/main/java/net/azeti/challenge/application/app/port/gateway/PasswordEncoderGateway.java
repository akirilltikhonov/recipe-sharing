package net.azeti.challenge.application.app.port.gateway;

import net.azeti.challenge.application.app.port.gateway.mapping.EncodePasswordEncoderAdapter;

public interface PasswordEncoderGateway {

    @EncodePasswordEncoderAdapter
    String encode(String rawPassword);
}
