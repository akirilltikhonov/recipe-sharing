package net.azeti.challenge.application.integrationtest.generator;

import net.azeti.challenge.application.infra.jpa.entity.RecipeEntity;
import net.azeti.challenge.application.integrationtest.generator.pattern.RecipePattern;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static org.jeasy.random.FieldPredicates.named;

public class RecipeEntityGenerator {

    private final EasyRandom random = new EasyRandom(
            new EasyRandomParameters()
                    .stringLengthRange(20, 20)
                    .excludeField(named("recipeId")
                            .or(named("ingredients"))
                    )
                    .randomize(named("servings"), () -> ThreadLocalRandom.current().nextInt(1, 100))
    );

    public RecipeEntity nextRecipeEntity() {
        return nextRecipeEntity(null);
    }

    public RecipeEntity nextRecipeEntity(RecipePattern pattern) {
        var recipe = random.nextObject(RecipeEntity.class);
        if (pattern != null) {
            if (pattern.getTitle() != null) {
                recipe.setTitle(pattern.getTitle());
            }
            if (pattern.getUsername() != null) {
                recipe.setUsername(pattern.getUsername());
            }
            if (pattern.getDescription() != null) {
                recipe.setDescription(pattern.getDescription());
            }
            if (pattern.getInstructions() != null) {
                recipe.setInstructions(pattern.getInstructions());
            }
            if (pattern.getServings() != null) {
                recipe.setServings(pattern.getServings());
            }
        }
        return recipe;
    }
}