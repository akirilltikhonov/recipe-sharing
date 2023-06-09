package net.azeti.challenge.application.infra.jpa.repository.impl;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.application.app.port.repository.RecipeRepository;
import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.domain.exception.notfound.RecipeNotFoundException;
import net.azeti.challenge.application.domain.filter.RecipeFilter;
import net.azeti.challenge.application.infra.jpa.mapper.RecipeMapper;
import net.azeti.challenge.application.infra.jpa.repository.RecipeJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public Optional<Recipe> getById(Long recipeId) {
        return recipeJpaRepository.findById(recipeId)
                .map(recipeMapper::toRecipe);
    }

    @Override
    @Transactional
    public Recipe update(Recipe recipe) {
        recipeJpaRepository.findById(recipe.getRecipeId())
                .orElseThrow(() -> new RecipeNotFoundException(recipe.getRecipeId()));
        var toUpdate = recipeMapper.toRecipeEntity(recipe);
        return recipeMapper.toRecipe(recipeJpaRepository.save(toUpdate));
    }

    @Override
    public Recipe delete(Long recipeId) {
        var recipe = recipeMapper.toRecipe(recipeJpaRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(recipeId)));
        recipeJpaRepository.deleteById(recipeId);
        return recipe;
    }

    @Override
    public List<Recipe> getByUser(String username) {
        return recipeMapper.toRecipes(recipeJpaRepository.findByUsername(username));
    }

    @Override
    public List<Recipe> findByFilter(RecipeFilter filter) {
        return recipeMapper.toRecipes(recipeJpaRepository.findByFilter(filter));
    }
}