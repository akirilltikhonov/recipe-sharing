package net.azeti.challenge.api.dto.authentification;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
public record RegistrationDto(
        @NotNull
        String username,
        @NotNull
        String password,
        @NotNull
        @Email
        String email
) {
}