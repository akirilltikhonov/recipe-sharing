package net.azeti.challenge.application.domain;

import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class Recipe {

    Long recipeId;
    String title;
    String username;
    String description;
    String instructions;
    Integer servings;
    @Builder.Default
    List<Ingredient> ingredients = new ArrayList<>();
}