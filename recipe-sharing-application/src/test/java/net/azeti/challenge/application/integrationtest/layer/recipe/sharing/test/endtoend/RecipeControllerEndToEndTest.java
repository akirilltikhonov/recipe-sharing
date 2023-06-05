package net.azeti.challenge.application.integrationtest.layer.recipe.sharing.test.endtoend;

import net.azeti.challenge.api.dto.CreateIngredientDto;
import net.azeti.challenge.api.dto.CreateRecipeDto;
import net.azeti.challenge.api.dto.IngredientDto;
import net.azeti.challenge.api.dto.UpdateRecipeDto;
import net.azeti.challenge.api.enums.Unit;
import net.azeti.challenge.application.infra.api.rest.controller.RecipeController;
import net.azeti.challenge.application.integrationtest.layer.recipe.sharing.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RecipeControllerEndToEndTest extends ApplicationTest {

    @Autowired
    private RecipeController recipeController;

    @Test
    @WithMockUser(authorities={"READ", "WRITE"})
    void createGetUpdateDelete() {
        String username = UUID.randomUUID().toString();
        var createIngredientDto1 = CreateIngredientDto.builder()
                .value(1000f)
                .unit(Unit.ML)
                .type("milk")
                .build();
        var createRecipeDto = CreateRecipeDto.builder()
                .title("Omelette")
                .username(username)
                .description("best ever")
                .instructions("just add all")
                .servings(1)
                .ingredients(List.of(createIngredientDto1))
                .build();

        var recipe = recipeController.create(createRecipeDto).getBody();
        assertThat(createRecipeDto)
                .usingRecursiveComparison()
                .isEqualTo(recipe);
        Long recipeId = Objects.requireNonNull(recipe).recipeId();

        assertThat(recipeController.getById(recipeId).getBody())
                .isEqualTo(recipe);

        var ingredientDto2 = IngredientDto.builder()
                .value(8f)
                .unit(Unit.PC)
                .type("egs")
                .build();
        var updateRecipe = UpdateRecipeDto.builder()
                .title(recipe.title())
                .username(recipe.username())
                .description(recipe.description())
                .instructions(recipe.instructions())
                .servings(recipe.servings())
                .ingredients(List.of(recipe.ingredients().iterator().next(), ingredientDto2))
                .build();

        var updatedRecipe = recipeController.update(recipeId, updateRecipe).getBody();
        assertThat(updatedRecipe)
                .usingRecursiveComparison()
                .ignoringFields("ingredients")
                .isEqualTo(recipe);
        assertThat(updatedRecipe.ingredients())
                .hasSize(2)
                .contains(recipe.ingredients().iterator().next());

        recipeController.delete(recipeId).getBody();
    }
}