package net.azeti.challenge.api.dto.authentification;

import lombok.Builder;

@Builder(toBuilder = true)
public record RegistrationDto(
        String username,
        String password,
        String email
) {
}