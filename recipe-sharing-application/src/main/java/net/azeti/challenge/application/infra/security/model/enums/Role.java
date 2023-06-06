package net.azeti.challenge.application.infra.security.model.enums;

import java.util.Set;

public enum Role {

    RO(Set.of(Authority.READ)),
    RW(Set.of(Authority.READ, Authority.WRITE));

    private final Set<Authority> authorities;

    Role(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }
}
