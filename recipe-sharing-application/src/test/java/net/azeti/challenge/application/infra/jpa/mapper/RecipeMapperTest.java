package net.azeti.challenge.application.infra.jpa.mapper;

import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.infra.jpa.entity.RecipeEntity;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RecipeMapperTest {

    private final RecipeMapper recipeMapper = new RecipeMapperImpl(new IngredientMapperImpl());

    private final EasyRandom random = new EasyRandom(
            new EasyRandomParameters()
                    .seed(1)
                    .collectionSizeRange(1, 3)
    );

    @Test
    void toRecipeEntity() {
        var recipe = random.nextObject(Recipe.class);
        var recipeEntity = recipeMapper.toRecipeEntity(recipe);

        assertThat(recipeEntity)
                .usingRecursiveComparison()
                .ignoringFields("ingredients.recipe")
                .isEqualTo(recipe);
        recipeEntity.getIngredients().forEach(i -> assertThat(i.getRecipe()).isNull());
    }

    @Test
    void toRecipe() {
        var recipeEntity = random.nextObject(RecipeEntity.class);
        var recipe = recipeMapper.toRecipe(recipeEntity);

        assertThat(recipe)
                .usingRecursiveComparison()
                .isEqualTo(recipeEntity);
    }
}