package net.azeti.challenge.application.infra.jpa.repository.impl;

import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.infra.jpa.mapper.RecipeMapper;
import net.azeti.challenge.application.infra.jpa.repository.RecipeJpaRepository;
import net.azeti.challenge.application.integrationtest.generator.RecipeEntityGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

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
}