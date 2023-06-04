package net.azeti.challenge.api.dto;

import lombok.Builder;
import net.azeti.challenge.api.enums.Unit;

@Builder
public record IngredientDto(
        Long ingredientId,
        Float value,
        Unit unit,
        String type
) {
}
