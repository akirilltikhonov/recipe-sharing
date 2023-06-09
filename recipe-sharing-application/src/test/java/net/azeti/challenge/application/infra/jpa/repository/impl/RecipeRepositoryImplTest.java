package net.azeti.challenge.application.infra.jpa.repository.impl;

import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.domain.filter.RecipeFilter;
import net.azeti.challenge.application.infra.jpa.entity.RecipeEntity;
import net.azeti.challenge.application.infra.jpa.mapper.RecipeMapper;
import net.azeti.challenge.application.infra.jpa.repository.RecipeJpaRepository;
import net.azeti.challenge.application.integrationtest.generator.RecipeEntityGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecipeRepositoryImplTest {

    private final RecipeEntityGenerator recipeEntityGenerator = new RecipeEntityGenerator();
    @InjectMocks
    private RecipeRepositoryImpl recipeRepository;
    @Mock
    private RecipeJpaRepository recipeJpaRepository;
    @Mock
    private RecipeMapper recipeMapper;

    @Test
    void create() {
        var recipe = Recipe.builder()
                .build();

        var recipeEntityToSave = recipeEntityGenerator.nextRecipeEntity();
        doReturn(recipeEntityToSave).when(recipeMapper).toRecipeEntity(recipe);
        var savedRecipeEntity = recipeEntityGenerator.nextRecipeEntity();
        doReturn(savedRecipeEntity).when(recipeJpaRepository).save(recipeEntityToSave);
        var savedRecipe = Recipe.builder()
                .recipeId(1L)
                .build();
        doReturn(savedRecipe).when(recipeMapper).toRecipe(savedRecipeEntity);
        assertThat(recipeRepository.create(recipe)).isEqualTo(savedRecipe);
    }

    @Test
    void getById() {
        var recipeEntity = recipeEntityGenerator.nextRecipeEntity();
        doReturn(Optional.of(recipeEntity)).when(recipeJpaRepository).findById(1L);
        var recipe = Recipe.builder()
                .recipeId(1L)
                .build();
        doReturn(recipe).when(recipeMapper).toRecipe(recipeEntity);
        assertThat(recipeRepository.getById(1L)).isEqualTo(Optional.of(recipe));
    }

    @Test
    void update() {
        var recipe = Recipe.builder()
                .recipeId(1L)
                .build();

        doReturn(Optional.of(new RecipeEntity())).when(recipeJpaRepository).findById(1L);
        var recipeEntity = recipeEntityGenerator.nextRecipeEntity();
        doReturn(recipeEntity).when(recipeMapper).toRecipeEntity(recipe);
        doReturn(recipeEntity).when(recipeJpaRepository).save(recipeEntity);
        doReturn(recipe).when(recipeMapper).toRecipe(recipeEntity);

        assertThat(recipeRepository.update(recipe)).isEqualTo(recipe);
        verify(recipeJpaRepository).findById(1L);
    }

    @Test
    void delete() {
        var recipeEntity = recipeEntityGenerator.nextRecipeEntity();
        doReturn(Optional.of(recipeEntity)).when(recipeJpaRepository).findById(1L);
        var recipe = Recipe.builder()
                .recipeId(1L)
                .build();
        doReturn(recipe).when(recipeMapper).toRecipe(recipeEntity);
        assertThat(recipeRepository.delete(1L)).isEqualTo(recipe);

        verify(recipeJpaRepository).deleteById(1L);
    }

    @Test
    void getByUser() {
        var recipeEntities = List.of(recipeEntityGenerator.nextRecipeEntity());
        doReturn(recipeEntities).when(recipeJpaRepository).findByUsername("name");
        var recipes = List.of(Recipe.builder()
                .recipeId(1L)
                .build());
        doReturn(recipes).when(recipeMapper).toRecipes(recipeEntities);
        assertThat(recipeRepository.getByUser("name")).isEqualTo(recipes);
    }

    @Test
    void findByFilter() {
        var filter = RecipeFilter.builder().build();

        var recipeEntities = List.of(recipeEntityGenerator.nextRecipeEntity());
        doReturn(recipeEntities).when(recipeJpaRepository).findByFilter(filter);
        var recipes = List.of(Recipe.builder()
                .recipeId(1L)
                .build());
        doReturn(recipes).when(recipeMapper).toRecipes(recipeEntities);
        assertThat(recipeRepository.findByFilter(RecipeFilter.builder().build())).isEqualTo(recipes);
    }
}