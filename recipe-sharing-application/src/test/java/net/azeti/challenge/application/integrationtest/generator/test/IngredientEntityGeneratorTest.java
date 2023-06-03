package net.azeti.challenge.application.integrationtest.generator.test;

import net.azeti.challenge.application.domain.enums.Unit;
import net.azeti.challenge.application.infra.jpa.entity.IngredientEntity;
import net.azeti.challenge.application.integrationtest.generator.IngredientEntityGenerator;
import net.azeti.challenge.application.integrationtest.generator.pattern.IngredientPattern;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IngredientEntityGeneratorTest {

    private final IngredientEntityGenerator generator = new IngredientEntityGenerator();

    private void assertNextIngredientEntity(IngredientEntity r) {
        assertThat(r).isNotNull();
        assertThat(r).hasNoNullFieldsOrPropertiesExcept("ingredientId", "recipe");
        assertThat(r).hasAllNullFieldsOrPropertiesExcept(
                "value"
                , "unit"
                , "type"
        );
    }

    @Test
    void nextIngredientEntity() {
        assertNextIngredientEntity(generator.nextIngredientEntity());
    }

    @Test
    void nextIngredientEntityWithNullPattern() {
        assertNextIngredientEntity(generator.nextIngredientEntity(null));
    }

    @Test
    void nextIngredientEntityWithPattern() {
        final var pattern = IngredientPattern.builder()
                .value(123.456f)
                .unit(Unit.G)
                .type("type")
                .build();
        final var ingredient = generator.nextIngredientEntity(pattern);
        assertThat(pattern)
                .usingRecursiveComparison()
                .isEqualTo(ingredient);
    }

    @Test
    void nextIngredientEntityWithPatternAssertionError() {
        final IngredientPattern pattern = IngredientPattern.builder().build();
        final IngredientEntity contract = generator.nextIngredientEntity(pattern);
        assertThatThrownBy(() -> assertThat(pattern).usingRecursiveComparison().isEqualTo(contract))
                .isInstanceOf(AssertionError.class);
    }
}