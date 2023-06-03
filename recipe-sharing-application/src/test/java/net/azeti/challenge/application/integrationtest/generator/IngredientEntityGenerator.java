package net.azeti.challenge.application.integrationtest.generator;

import net.azeti.challenge.application.infra.jpa.entity.IngredientEntity;
import net.azeti.challenge.application.integrationtest.generator.pattern.IngredientPattern;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import static org.jeasy.random.FieldPredicates.named;

public class IngredientEntityGenerator {

    private final EasyRandom random = new EasyRandom(
            new EasyRandomParameters()
                    .stringLengthRange(20, 20)
                    .excludeField(named("ingredientId")
                            .or(named("recipe"))
                    )
    );

    public IngredientEntity nextIngredientEntity() {
        return nextIngredientEntity(null);
    }

    public IngredientEntity nextIngredientEntity(IngredientPattern pattern) {
        var ingredient = random.nextObject(IngredientEntity.class);
        if (pattern != null) {
            if (pattern.getValue() != null) {
                ingredient.setValue(pattern.getValue());
            }
            if (pattern.getUnit() != null) {
                ingredient.setUnit(pattern.getUnit());
            }
            if (pattern.getType() != null) {
                ingredient.setType(pattern.getType());
            }
        }
        return ingredient;
    }
}