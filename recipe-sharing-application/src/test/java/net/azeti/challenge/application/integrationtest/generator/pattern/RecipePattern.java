package net.azeti.challenge.application.integrationtest.generator.pattern;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class RecipePattern {

    String title;
    String username;
    String description;
    String instructions;
    Integer servings;
}
