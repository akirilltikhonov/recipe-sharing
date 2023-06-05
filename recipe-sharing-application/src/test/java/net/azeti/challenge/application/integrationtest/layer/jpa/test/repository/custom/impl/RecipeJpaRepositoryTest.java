package net.azeti.challenge.application.integrationtest.layer.jpa.test.repository.custom.impl;

import net.azeti.challenge.application.app.port.repository.RecipeRepository;
import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.domain.filter.RecipeFilter;
import net.azeti.challenge.application.infra.jpa.repository.RecipeJpaRepository;
import net.azeti.challenge.application.integrationtest.generator.IngredientEntityGenerator;
import net.azeti.challenge.application.integrationtest.generator.RecipeEntityGenerator;
import net.azeti.challenge.application.integrationtest.generator.pattern.RecipePattern;
import net.azeti.challenge.application.integrationtest.layer.jpa.ApplicationJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RecipeJpaRepositoryTest extends ApplicationJpaTest {

    private final RecipeEntityGenerator recipeEntityGenerator = new RecipeEntityGenerator();
    private final IngredientEntityGenerator ingredientEntityGenerator = new IngredientEntityGenerator();
    @Autowired
    private RecipeJpaRepository recipeJpaRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    private Recipe recipe1;
    private Recipe recipe2;
    private String tittle;
    private String username1;

    @PostConstruct
    void setUp() {
        tittle = UUID.randomUUID().toString();
        username1 = UUID.randomUUID().toString();
        var recipeEntity1 = recipeEntityGenerator.nextRecipeEntity(RecipePattern.builder()
                .title("SUPER " + tittle.toUpperCase() + " BREAKFAST")
                .username(username1)
                .build());
        recipeEntity1.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeEntity1.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeEntity1.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeEntity1 = recipeJpaRepository.save(recipeEntity1);
        recipe1 = recipeRepository.getById(recipeEntity1.getRecipeId()).orElseThrow();

        var username2 = UUID.randomUUID().toString();
        var recipeEntity2 = recipeEntityGenerator.nextRecipeEntity(RecipePattern.builder()
                .title("super " + tittle + " launch")
                .username(username2)
                .build());
        recipeEntity2.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeEntity2.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeEntity2.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeEntity2 = recipeJpaRepository.save(recipeEntity2);
        recipe2 = recipeRepository.getById(recipeEntity2.getRecipeId()).orElseThrow();
    }

    @Test
    void findByFilterTitle() {
        var found = recipeRepository.findByFilter(RecipeFilter.builder()
                .titleLike(tittle)
                .build());
        assertThat(found)
                .hasSize(2)
                .contains(recipe1)
                .contains(recipe2);
    }

    @Test
    void findByFilterTitleAndUsername() {
        var found = recipeRepository.findByFilter(RecipeFilter.builder()
                .titleLike(tittle)
                .usernameLike(username1.substring(0, 7))
                .build());
        assertThat(found)
                .hasSize(1)
                .contains(recipe1);
    }

    @Test
    void findByFilterAll() {
        recipeRepository.findByFilter(RecipeFilter.builder().build());
    }
}