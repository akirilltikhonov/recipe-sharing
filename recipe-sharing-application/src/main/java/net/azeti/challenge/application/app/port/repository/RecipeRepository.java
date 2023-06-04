package net.azeti.challenge.application.app.port.repository;

import net.azeti.challenge.application.domain.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository {

    Recipe create(Recipe recipe);

    Optional<Recipe> getById(Long id);

    Recipe update(Recipe recipe);

    Recipe delete(Long id);

    List<Recipe> getByUser(String username);
}
