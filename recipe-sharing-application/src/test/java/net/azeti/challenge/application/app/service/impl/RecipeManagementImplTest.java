package net.azeti.challenge.application.app.service.impl;

import net.azeti.challenge.application.app.port.repository.RecipeRepository;
import net.azeti.challenge.application.domain.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RecipeManagementImplTest {

    @InjectMocks
    private RecipeManagementImpl recipeManagement;
    @Mock
    private RecipeRepository recipeRepository;

    @Test
    void create() {
        var recipe = Recipe.builder()
                .build();

        var savedRecipe = Recipe.builder()
                .recipeId(1L)
                .build();
        doReturn(savedRecipe).when(recipeRepository).create(recipe);

        assertThat(recipeManagement.create(recipe)).isEqualTo(savedRecipe);
    }

    @Test
    void getById() {
        var recipe = Recipe.builder()
                .recipeId(1L)
                .build();
        doReturn(Optional.of(recipe)).when(recipeRepository).getById(1L);
        assertThat(recipeManagement.getById(1L)).isEqualTo(Optional.of(recipe));
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getByUser() {
    }
}