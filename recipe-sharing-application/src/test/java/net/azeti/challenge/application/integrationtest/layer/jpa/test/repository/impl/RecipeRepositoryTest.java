package net.azeti.challenge.application.integrationtest.layer.jpa.test.repository.impl;

import net.azeti.challenge.application.app.port.repository.RecipeRepository;
import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.infra.jpa.mapper.RecipeMapper;
import net.azeti.challenge.application.infra.jpa.repository.RecipeJpaRepository;
import net.azeti.challenge.application.integrationtest.generator.IngredientEntityGenerator;
import net.azeti.challenge.application.integrationtest.generator.RecipeEntityGenerator;
import net.azeti.challenge.application.integrationtest.layer.jpa.ApplicationJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class RecipeRepositoryTest extends ApplicationJpaTest {

    private final RecipeEntityGenerator recipeEntityGenerator = new RecipeEntityGenerator();
    private final IngredientEntityGenerator ingredientEntityGenerator = new IngredientEntityGenerator();
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeJpaRepository recipeJpaRepository;
    @Autowired
    private RecipeMapper recipeMapper;

    @Test
    void create() {
        var ingredientEntity1 = ingredientEntityGenerator.nextIngredientEntity();
        var ingredientEntity2 = ingredientEntityGenerator.nextIngredientEntity();
        var ingredientEntity3 = ingredientEntityGenerator.nextIngredientEntity();
        var recipeEntity = recipeEntityGenerator.nextRecipeEntity();
        recipeEntity.addIngredient(ingredientEntity1);
        recipeEntity.addIngredient(ingredientEntity2);
        recipeEntity.addIngredient(ingredientEntity3);
        assertThat(recipeEntity.getRecipeId()).isNull();
        recipeEntity.getIngredients().forEach(i -> assertThat(i.getIngredientId()).isNull());

        var recipe = recipeMapper.toRecipe(recipeEntity);
        var createdRecipe = recipeRepository.create(recipe);

        assertThat(createdRecipe)
                .usingRecursiveComparison()
                .ignoringFields("recipeId", "ingredients.ingredientId")
                .isEqualTo(recipe);
        assertThat(createdRecipe.getRecipeId()).isNotNull();
        createdRecipe.getIngredients().forEach(i -> assertThat(i.getIngredientId()).isNotNull());
    }
}
