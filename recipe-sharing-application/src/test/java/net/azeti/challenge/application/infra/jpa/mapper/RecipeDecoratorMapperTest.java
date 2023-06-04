package net.azeti.challenge.application.infra.jpa.mapper;

import net.azeti.challenge.application.domain.Recipe;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RecipeDecoratorMapperTest {

    private final RecipeMapper recipeMapper = new RecipeMapperImpl_(new IngredientMapperImpl());
    private final RecipeDecoratorMapper recipeDecoratorMapper = new RecipeMapperImpl(recipeMapper);

    private final EasyRandom random = new EasyRandom(
            new EasyRandomParameters()
                    .seed(1)
                    .collectionSizeRange(1, 3)
    );

    @BeforeEach
    void setUp() {
        recipeDecoratorMapper.setRecipeMapper(recipeMapper);
    }

    @Test
    void toRecipeEntity() {
        var recipe = random.nextObject(Recipe.class);
        var recipeEntity = recipeDecoratorMapper.toRecipeEntity(recipe);

        assertThat(recipeEntity)
                .usingRecursiveComparison()
                .ignoringFields("ingredients.recipe")
                .isEqualTo(recipe);
        recipeEntity.getIngredients().forEach(i -> assertThat(i.getRecipe()).isEqualTo(recipeEntity));
    }
}