package net.azeti.challenge.api.dto;

import lombok.Builder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder(toBuilder = true)
public record RecipeDto(
        Long recipeId,
        @NotNull
        String title,
        @NotNull
        String username,
        String description,
        @NotNull
        String instructions,
        @Min(1)
        Integer servings,
        @NotNull
        @NotEmpty
        List<@NotNull IngredientDto> ingredients
) {
}
