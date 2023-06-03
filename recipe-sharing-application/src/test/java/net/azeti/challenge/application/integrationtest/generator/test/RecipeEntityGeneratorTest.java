package net.azeti.challenge.application.integrationtest.generator.test;

import net.azeti.challenge.application.infra.jpa.entity.RecipeEntity;
import net.azeti.challenge.application.integrationtest.generator.RecipeEntityGenerator;
import net.azeti.challenge.application.integrationtest.generator.pattern.RecipePattern;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RecipeEntityGeneratorTest {

    private final RecipeEntityGenerator generator = new RecipeEntityGenerator();

    private void assertNextRecipeEntity(RecipeEntity r) {
        assertThat(r).isNotNull();
        assertThat(r).hasNoNullFieldsOrPropertiesExcept("recipeId");
        assertThat(r).hasAllNullFieldsOrPropertiesExcept(
                "title"
                , "username"
                , "description"
                , "instructions"
                , "servings"
        );
    }

    @Test
    void nextRecipeEntity() {
        assertNextRecipeEntity(generator.nextRecipeEntity());
    }

    @Test
    void nextRecipeEntityWithNullPattern() {
        assertNextRecipeEntity(generator.nextRecipeEntity(null));
    }

    @Test
    void nextRecipeEntityWithPattern() {
        BigDecimal testValue = BigDecimal.TEN;
        final var pattern = RecipePattern.builder()
                .title("title")
                .username("description")
                .description("description")
                .instructions("instructions")
                .servings(4)
                .build();
        final var recipe = generator.nextRecipeEntity(pattern);
        assertThat(pattern)
                .usingRecursiveComparison()
                .isEqualTo(recipe);
    }

    @Test
    void nextRecipeEntityWithPatternAssertionError() {
        final RecipePattern pattern = RecipePattern.builder().build();
        final RecipeEntity contract = generator.nextRecipeEntity(pattern);
        assertThatThrownBy(() -> assertThat(pattern).usingRecursiveComparison().isEqualTo(contract))
                .isInstanceOf(AssertionError.class);
    }
}