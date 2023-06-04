package net.azeti.challenge.api.dto;

import lombok.Builder;
import net.azeti.challenge.api.enums.Unit;

import javax.validation.constraints.NotNull;

@Builder
public record CreateIngredientDto(
        @NotNull
        Float value,
        @NotNull
        Unit unit,
        @NotNull
        String type
) {
}
