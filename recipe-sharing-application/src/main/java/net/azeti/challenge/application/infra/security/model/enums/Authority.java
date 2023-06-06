package net.azeti.challenge.application.infra.security.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    READ,
    WRITE;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
