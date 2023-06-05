package net.azeti.challenge.application.app.service.impl;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.application.app.port.repository.RecipeRepository;
import net.azeti.challenge.application.app.service.RecipeSearch;
import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.domain.filter.RecipeFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeSearchImpl implements RecipeSearch {

    private final RecipeRepository recipeRepository;

    @Override
    public List<Recipe> recipesByUsername(String usernameValue) {
        return recipeRepository.findByFilter(RecipeFilter.builder()
                .usernameLike(usernameValue)
                .build());
    }

    @Override
    public List<Recipe> recipesByTitle(String titleValue) {
        return recipeRepository.findByFilter(RecipeFilter.builder()
                .titleLike(titleValue)
                .build());
    }

    @Override
    public List<Recipe> recipesByFilter(RecipeFilter filter) {
        return recipeRepository.findByFilter(filter);
    }
}
