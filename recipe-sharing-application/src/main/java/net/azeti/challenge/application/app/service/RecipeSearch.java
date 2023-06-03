package net.azeti.challenge.application.app.service;

import net.azeti.challenge.application.domain.Recipe;

import java.util.List;

public interface RecipeSearch {

    List<Recipe> recipesByUsername(String usernameValue);

    List<Recipe> recipesByTitle(String titleValue);
}
