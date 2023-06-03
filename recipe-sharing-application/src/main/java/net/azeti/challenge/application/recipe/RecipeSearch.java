package net.azeti.challenge.application.recipe;

import java.util.List;

public interface RecipeSearch {

    List<Recipe> recipesByUsername(String usernameValue);

    List<Recipe> recipesByTitle(String titleValue);
}
