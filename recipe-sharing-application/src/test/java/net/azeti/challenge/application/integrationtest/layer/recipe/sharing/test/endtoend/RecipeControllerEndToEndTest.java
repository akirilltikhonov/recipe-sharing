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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class RecipeControllerEndToEndTest extends ApplicationTest {

    @Autowired
    private RecipeController recipeController;
    @Value("${access-token.non-expiring}")
    private String accessToken;

    @Test
    @WithMockUser(authorities={"READ", "WRITE"})
    void createGetUpdateDelete() {
        var createIngredientDto1 = CreateIngredientDto.builder()
                .value(1000f)
                .unit(Unit.ML)
                .type("milk")
                .build();
        var createRecipeDto = CreateRecipeDto.builder()
                .title("Omelette")
                .description("best ever")
                .instructions("just add all")
                .servings(1)
                .ingredients(List.of(createIngredientDto1))
                .build();

        var recipe = recipeController.create(createRecipeDto, accessToken).getBody();
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
                .description(recipe.description())
                .instructions(recipe.instructions())
                .servings(recipe.servings())
                .ingredients(List.of(recipe.ingredients().iterator().next(), ingredientDto2))
                .build();

        var updatedRecipe = recipeController.update(recipeId, updateRecipe, accessToken).getBody();
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