package net.azeti.challenge.application.app.port.repository;

import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.domain.filter.RecipeFilter;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository {

    Recipe create(Recipe recipe);

    Optional<Recipe> getById(Long recipeId);

    Recipe update(Recipe recipe);

    Recipe delete(Long recipeId);

    List<Recipe> getByUser(String username);

    List<Recipe> findByFilter(RecipeFilter filter);
}
