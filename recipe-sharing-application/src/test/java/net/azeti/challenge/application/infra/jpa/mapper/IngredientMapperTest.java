package net.azeti.challenge.application.infra.jpa.mapper;

import net.azeti.challenge.application.domain.Ingredient;
import net.azeti.challenge.application.infra.jpa.entity.IngredientEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IngredientMapperTest {

    private final IngredientMapper ingredientMapper = new IngredientMapperImpl();

    private final EasyRandom random = new EasyRandom();

    @Test
    void toIngredientEntity() {
        var ingredient = random.nextObject(Ingredient.class);
        var ingredientEntity = ingredientMapper.toIngredientEntity(ingredient);

        assertThat(ingredientEntity)
                .usingRecursiveComparison()
                .ignoringFields("recipe")
                .isEqualTo(ingredient);
        assertThat(ingredientEntity.getRecipe())
                .isNull();
    }

    @Test
    void toIngredient() {
        var ingredientEntity = random.nextObject(IngredientEntity.class);
        var ingredient = ingredientMapper.toIngredient(ingredientEntity);

        assertThat(ingredient)
                .usingRecursiveComparison()
                .ignoringFields("recipe")
                .isEqualTo(ingredientEntity);
    }
}