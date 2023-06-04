package net.azeti.challenge.api.dto;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record UpdateRecipeDto(
        String title,
        String username,
        String description,
        String instructions,
        Integer servings,
        List<IngredientDto> ingredients
) {
}
