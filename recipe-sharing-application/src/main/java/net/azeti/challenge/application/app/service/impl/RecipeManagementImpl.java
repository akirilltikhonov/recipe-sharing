package net.azeti.challenge.application.app.service.impl;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.application.app.port.repository.RecipeRepository;
import net.azeti.challenge.application.app.service.RecipeManagement;
import net.azeti.challenge.application.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeManagementImpl implements RecipeManagement {

    private final RecipeRepository recipeRepository;

    @Override
    public Recipe create(Recipe recipe) {
        return recipeRepository.create(recipe);
    }

    @Override
    public Optional<Recipe> getById(Long id) {
        return recipeRepository.getById(id);
    }

    @Override
    public Recipe update(Long id, Recipe recipe) {
        return null;
    }

    @Override
    public Recipe delete(Long id) {
        return null;
    }

    @Override
    public List<Recipe> getByUser(String username) {
        return null;
    }
}
