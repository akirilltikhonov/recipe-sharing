package net.azeti.challenge.application.infra.jpa.repository.impl;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.application.app.port.repository.RecipeRepository;
import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.infra.jpa.mapper.RecipeMapper;
import net.azeti.challenge.application.infra.jpa.repository.RecipeJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeRepositoryImpl implements RecipeRepository {

    private final RecipeJpaRepository recipeJpaRepository;
    private final RecipeMapper recipeMapper;

    @Override
    public Recipe create(Recipe recipe) {
        var recipeEntity = recipeMapper.toRecipeEntity(recipe);
        return recipeMapper.toRecipe(recipeJpaRepository.save(recipeEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Recipe> getById(Long id) {
        return recipeJpaRepository.findById(id)
                .map(recipeMapper::toRecipe);
    }

    @Override
    @Transactional
    public Recipe delete(Long id) {
        var recipe = recipeMapper.toRecipe(recipeJpaRepository.findById(id)
                .orElseThrow());
        recipeJpaRepository.deleteById(id);
        return recipe;
    }
}