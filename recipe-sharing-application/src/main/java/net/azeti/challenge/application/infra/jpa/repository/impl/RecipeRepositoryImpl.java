package net.azeti.challenge.application.infra.jpa.repository.impl;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.application.app.port.repository.RecipeRepository;
import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.infra.jpa.mapper.RecipeMapper;
import net.azeti.challenge.application.infra.jpa.repository.RecipeJpaRepository;
import org.springframework.stereotype.Service;

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
}
