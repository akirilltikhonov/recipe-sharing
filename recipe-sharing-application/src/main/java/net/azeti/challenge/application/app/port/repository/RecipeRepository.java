package net.azeti.challenge.application.app.port.repository;

import net.azeti.challenge.application.domain.Recipe;

import java.util.Optional;

public interface RecipeRepository {

    Recipe create(Recipe recipe);

    Optional<Recipe> getById(Long id);
}
