package net.azeti.challenge.api.dto.authentification;

import lombok.Builder;

@Builder(toBuilder = true)
public record TokenDto(
        String accessToken
) {
}
