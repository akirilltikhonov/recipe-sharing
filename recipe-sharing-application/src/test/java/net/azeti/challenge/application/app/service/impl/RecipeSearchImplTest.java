package net.azeti.challenge.application.app.service.impl;

import net.azeti.challenge.application.app.port.repository.RecipeRepository;
import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.domain.filter.RecipeFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RecipeSearchImplTest {

    @InjectMocks
    private RecipeSearchImpl recipeSearch;
    @Mock
    private RecipeRepository recipeRepository;

    @Test
    void recipesByUsername() {
        String usernameValue = "usernameValue";

        var recipes = List.of(Recipe.builder().build());
        doReturn(recipes).when(recipeRepository).findByFilter(RecipeFilter.builder()
                .usernameLike(usernameValue)
                .build());

        assertThat(recipeSearch.recipesByUsername(usernameValue))
                .isEqualTo(recipes);
    }

    @Test
    void recipesByTitle() {
        String titleValue = "titleValue";

        var recipes = List.of(Recipe.builder().build());
        doReturn(recipes).when(recipeRepository).findByFilter(RecipeFilter.builder()
                .titleLike(titleValue)
                .build());

        assertThat(recipeSearch.recipesByTitle(titleValue))
                .isEqualTo(recipes);
    }

    @Test
    void recipesByFilter() {
        var filter = RecipeFilter.builder()
                .build();

        var recipes = List.of(Recipe.builder().build());
        doReturn(recipes).when(recipeRepository).findByFilter(filter);

        assertThat(recipeSearch.recipesByFilter(filter))
                .isEqualTo(recipes);
    }
}