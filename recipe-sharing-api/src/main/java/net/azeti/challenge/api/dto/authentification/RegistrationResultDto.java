package net.azeti.challenge.api.dto.authentification;

import lombok.Builder;

@Builder(toBuilder = true)
public record RegistrationResultDto(
        String username,
        String email,
        TokenDto token
) {
}