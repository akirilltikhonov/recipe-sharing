package net.azeti.challenge.application.app.port.repository;

import net.azeti.challenge.application.domain.Recipe;

public interface RecipeRepository {
    Recipe create(Recipe recipe);
}
