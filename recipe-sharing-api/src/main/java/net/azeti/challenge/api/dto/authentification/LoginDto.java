package net.azeti.challenge.api.dto.authentification;

import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
public record LoginDto(
        @NotNull
        String username,
        @NotNull
        String password
) {
}
