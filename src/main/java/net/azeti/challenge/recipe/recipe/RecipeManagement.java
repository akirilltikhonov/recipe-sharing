package net.azeti.challenge.recipe.recipe;

import java.util.List;
import java.util.Optional;

// This class assumes the Recipe's id is a Long, this can be changed if needed.
public interface RecipeManagement {

    Recipe create(Recipe recipe);

    Optional<Recipe> getById(Long id);

    Recipe update(Long id, Recipe recipe);

    Recipe delete(Long id);

    List<Recipe> getByUser(String username);
}
