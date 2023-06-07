package net.azeti.challenge.application.infra.api.rest.controller;

import net.azeti.challenge.api.dto.CreateRecipeDto;
import net.azeti.challenge.api.dto.RecipeDto;
import net.azeti.challenge.api.dto.RecipeFilterDto;
import net.azeti.challenge.api.dto.UpdateRecipeDto;
import net.azeti.challenge.application.app.service.RecipeManagement;
import net.azeti.challenge.application.app.service.RecipeSearch;
import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.domain.filter.RecipeFilter;
import net.azeti.challenge.application.infra.api.rest.mapper.request.RecipeRequestMapper;
import net.azeti.challenge.application.infra.api.rest.mapper.response.RecipeResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

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
    private RecipeSearch recipeSearch;
    @Mock
    private RecipeResponseMapper recipeResponseMapper;

    @Test
    void create() {
        String accessToken = "accessToken";
        var createRecipeDto = CreateRecipeDto.builder()
                .build();

        var recipe = Recipe.builder().build();
        doReturn(recipe).when(recipeRequestMapper).toRecipe(createRecipeDto, accessToken);
        var createdRecipe = recipe.toBuilder().recipeId(1L).build();
        doReturn(createdRecipe).when(recipeManagement).create(recipe);
        var recipeDto = RecipeDto.builder().recipeId(1L).build();
        doReturn(recipeDto).when(recipeResponseMapper).toRecipeDto(createdRecipe);

        assertThat(recipeController.create(createRecipeDto, accessToken)).isEqualTo(ResponseEntity.ok(recipeDto));
    }

    @Test
    void getById() {
        var recipe = Recipe.builder().recipeId(1L).build();
        doReturn(Optional.of(recipe)).when(recipeManagement).getById(1L);
        var recipeDto = RecipeDto.builder().recipeId(1L).build();
        doReturn(recipeDto).when(recipeResponseMapper).toRecipeDto(recipe.toBuilder().build());

        assertThat(recipeController.getById(1L)).isEqualTo(ResponseEntity.ok(recipeDto));
    }

    @Test
    void update() {
        var updateRecipeDto = UpdateRecipeDto.builder()
                .build();

        var recipe = Recipe.builder().build();
        doReturn(recipe).when(recipeRequestMapper).toRecipe(updateRecipeDto);
        var updatedRecipe = recipe.toBuilder().recipeId(1L).build();
        doReturn(updatedRecipe).when(recipeManagement).update(1L, recipe);
        var recipeDto = RecipeDto.builder().recipeId(1L).build();
        doReturn(recipeDto).when(recipeResponseMapper).toRecipeDto(updatedRecipe);

        assertThat(recipeController.update(1L, updateRecipeDto)).isEqualTo(ResponseEntity.ok(recipeDto));
    }

    @Test
    void delete() {
        var recipe = Recipe.builder().recipeId(1L).build();
        doReturn(recipe).when(recipeManagement).delete(1L);
        var recipeDto = RecipeDto.builder().recipeId(1L).build();
        doReturn(recipeDto).when(recipeResponseMapper).toRecipeDto(recipe);

        assertThat(recipeController.delete(1L)).isEqualTo(ResponseEntity.ok(recipeDto));
    }

    @Test
    void getByUser() {
        String username = "username";
        var recipes = List.of(Recipe.builder().username("username").build());
        doReturn(recipes).when(recipeManagement).getByUser(username);
        var recipeDtos = List.of(RecipeDto.builder().username("username").build());
        doReturn(recipeDtos).when(recipeResponseMapper).toRecipeDtos(recipes);

        assertThat(recipeController.getByUser(username)).isEqualTo(ResponseEntity.ok(recipeDtos));
    }

    @Test
    void findByFilter() {
        var filterDto = RecipeFilterDto.builder().build();

        var filter = RecipeFilter.builder().build();
        doReturn(filter).when(recipeRequestMapper).toRecipeFilter(filterDto);
        var recipes = List.of(Recipe.builder().username("username").build());
        doReturn(recipes).when(recipeSearch).recipesByFilter(filter);
        var recipeDtos = List.of(RecipeDto.builder().username("username").build());
        doReturn(recipeDtos).when(recipeResponseMapper).toRecipeDtos(recipes);

        assertThat(recipeController.findByFilter(filterDto)).isEqualTo(ResponseEntity.ok(recipeDtos));
    }
}