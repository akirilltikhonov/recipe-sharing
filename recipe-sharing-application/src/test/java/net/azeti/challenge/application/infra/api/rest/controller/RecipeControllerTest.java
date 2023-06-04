package net.azeti.challenge.application.infra.api.rest.controller;

import net.azeti.challenge.api.dto.CreateRecipeDto;
import net.azeti.challenge.api.dto.RecipeDto;
import net.azeti.challenge.application.app.service.RecipeManagement;
import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.infra.api.rest.mapper.request.RecipeRequestMapper;
import net.azeti.challenge.application.infra.api.rest.mapper.response.RecipeResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;
    @Mock
    private RecipeRequestMapper recipeRequestMapper;
    @Mock
    private RecipeManagement recipeManagement;
    @Mock
    private RecipeResponseMapper recipeResponseMapper;

    @Test
    void create() {
        var createRecipeDto = CreateRecipeDto.builder()
                .build();

        var recipe = Recipe.builder().build();
        doReturn(recipe).when(recipeRequestMapper).toRecipe(createRecipeDto);
        var createdRecipe = recipe.toBuilder().recipeId(1L).build();
        doReturn(createdRecipe).when(recipeManagement).create(recipe);
        var recipeDto = RecipeDto.builder().recipeId(1L).build();
        doReturn(recipeDto).when(recipeResponseMapper).toRecipeDto(recipe.toBuilder().build());

        assertThat(recipeController.create(createRecipeDto)).isEqualTo(ResponseEntity.ok(recipeDto));
    }
}