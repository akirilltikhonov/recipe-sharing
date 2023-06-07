package net.azeti.challenge.application.integrationtest.layer.jpa.test.repository.impl;

import net.azeti.challenge.application.app.port.repository.RecipeRepository;
import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.domain.enums.Unit;
import net.azeti.challenge.application.domain.exception.notfound.RecipeNotFoundException;
import net.azeti.challenge.application.infra.jpa.mapper.RecipeMapper;
import net.azeti.challenge.application.infra.jpa.repository.RecipeJpaRepository;
import net.azeti.challenge.application.integrationtest.generator.IngredientEntityGenerator;
import net.azeti.challenge.application.integrationtest.generator.RecipeEntityGenerator;
import net.azeti.challenge.application.integrationtest.generator.pattern.RecipePattern;
import net.azeti.challenge.application.integrationtest.layer.jpa.ApplicationJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RecipeRepositoryTest extends ApplicationJpaTest {

    private final RecipeEntityGenerator recipeEntityGenerator = new RecipeEntityGenerator();
    private final IngredientEntityGenerator ingredientEntityGenerator = new IngredientEntityGenerator();
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeJpaRepository recipeJpaRepository;
    @Autowired
    private RecipeMapper recipeMapper;

    private Recipe recipeWith3Ingredients;
    private Recipe recipeToDelete;
    private Recipe recipeToByUser1;
    private Recipe recipeToByUser2;
    private Recipe recipeToByUser3;
    private Recipe recipeForUpdate;

    @PostConstruct
    void setUp() {
        var recipe = recipeEntityGenerator.nextRecipeEntity();
        recipe.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipe.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipe.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipe = recipeJpaRepository.save(recipe);
        recipeWith3Ingredients = recipeRepository.getById(recipe.getRecipeId())
                .orElseThrow();
        assertThat(recipeWith3Ingredients)
                .usingRecursiveComparison()
                .isEqualTo(recipe);

        var recipeEntityToUpdate = recipeEntityGenerator.nextRecipeEntity();
        recipeEntityToUpdate.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeEntityToUpdate.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeEntityToUpdate.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeEntityToUpdate = recipeJpaRepository.save(recipeEntityToUpdate);
        recipeForUpdate = recipeRepository.getById(recipeEntityToUpdate.getRecipeId())
                .orElseThrow();

        var recipeEntityToDelete = recipeEntityGenerator.nextRecipeEntity();
        recipeEntityToDelete.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeEntityToDelete = recipeJpaRepository.save(recipeEntityToDelete);
        recipeToDelete = recipeRepository.getById(recipeEntityToDelete.getRecipeId())
                .orElseThrow();

        var usernamePattern = RecipePattern.builder()
                .username(UUID.randomUUID().toString())
                .build();
        var recipeEntityToGetByUser1 = recipeEntityGenerator.nextRecipeEntity(usernamePattern);
        recipeEntityToGetByUser1.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeEntityToGetByUser1 = recipeJpaRepository.save(recipeEntityToGetByUser1);
        recipeEntityToGetByUser1 = recipeJpaRepository.save(recipeEntityToGetByUser1);
        recipeToByUser1 = recipeRepository.getById(recipeEntityToGetByUser1.getRecipeId())
                .orElseThrow();
        var recipeEntityToGetByUser2 = recipeEntityGenerator.nextRecipeEntity(usernamePattern);
        recipeEntityToGetByUser2.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeEntityToGetByUser2 = recipeJpaRepository.save(recipeEntityToGetByUser2);
        recipeToByUser2 = recipeRepository.getById(recipeEntityToGetByUser2.getRecipeId())
                .orElseThrow();
        var recipeEntityToGetByUser3 = recipeEntityGenerator.nextRecipeEntity(usernamePattern);
        recipeEntityToGetByUser3 = recipeJpaRepository.save(recipeEntityToGetByUser3);
        recipeToByUser3 = recipeRepository.getById(recipeEntityToGetByUser3.getRecipeId())
                .orElseThrow();
    }

    @Test
    void create() {
        var ingredientEntity1 = ingredientEntityGenerator.nextIngredientEntity();
        var ingredientEntity2 = ingredientEntityGenerator.nextIngredientEntity();
        var ingredientEntity3 = ingredientEntityGenerator.nextIngredientEntity();
        var recipeEntity = recipeEntityGenerator.nextRecipeEntity();
        recipeEntity.addIngredient(ingredientEntity1);
        recipeEntity.addIngredient(ingredientEntity2);
        recipeEntity.addIngredient(ingredientEntity3);
        assertThat(recipeEntity.getRecipeId()).isNull();
        recipeEntity.getIngredients().forEach(i -> assertThat(i.getIngredientId()).isNull());

        var recipe = recipeMapper.toRecipe(recipeEntity);
        var createdRecipe = recipeRepository.create(recipe);

        assertThat(createdRecipe)
                .usingRecursiveComparison()
                .ignoringFields("recipeId", "ingredients.ingredientId")
                .isEqualTo(recipe);
        assertThat(createdRecipe.getRecipeId()).isNotNull();
        createdRecipe.getIngredients().forEach(i -> assertThat(i.getIngredientId()).isNotNull());
    }

    @Test
    void getById() {
        var recipe = recipeRepository.getById(recipeWith3Ingredients.getRecipeId()).orElseThrow();
        assertThat(recipe).isEqualTo(recipeWith3Ingredients);
        assertThat(recipe.getIngredients().size()).isEqualTo(3);
    }

    @Test
    void update() {
        var ingredientsForUpdate = recipeForUpdate.getIngredients();
        var ingredientToUpdate1 = ingredientsForUpdate.get(0).toBuilder()
                .ingredientId(null)
                .value(10000000000f)
                .build();
        var ingredientToUpdate2 = ingredientsForUpdate.get(1).toBuilder()
                .value(1000F)
                .unit(Unit.ML)
                .type("milk")
                .build();
        var recipeToUpdate = recipeForUpdate.toBuilder()
                .instructions("newInstructions")
                .servings(4)
                .ingredients(List.of(ingredientToUpdate1, ingredientToUpdate2))
                .build();

        var updated = recipeRepository.update(recipeToUpdate);

        assertThat(updated)
                .usingRecursiveComparison()
                .ignoringFields("ingredients")
                .isEqualTo(recipeToUpdate);

        assertThat(updated.getIngredients())
                .hasSize(2)
                .doesNotContain(ingredientsForUpdate.get(2))
                .contains(ingredientToUpdate2);
        var newIngredient = updated.getIngredients().stream()
                .filter(i -> !i.getIngredientId().equals(ingredientToUpdate2.getIngredientId()))
                .collect(Collectors.toList());
        assertThat(newIngredient).hasSize(1);
        assertThat(newIngredient.iterator().next())
                .usingRecursiveComparison()
                .ignoringFields("ingredientId")
                .isEqualTo(ingredientToUpdate1);
        assertThat(newIngredient.iterator().next().getIngredientId()).isNotNull();
    }

    @Test
    void updateNotFound() {
        assertThatThrownBy(() -> recipeRepository.update(recipeForUpdate.toBuilder().recipeId(null).build()))
                .isInstanceOf(InvalidDataAccessApiUsageException.class);
        assertThatThrownBy(() -> recipeRepository.update(recipeForUpdate.toBuilder().recipeId(999999999999999999L).build()))
                .isInstanceOf(RecipeNotFoundException.class);
    }

    @Test
    void delete() {
        assertThat(recipeRepository.delete(recipeToDelete.getRecipeId()))
                .isEqualTo(recipeToDelete);
    }

    @Test
    void deleteNotFound() {
        assertThatThrownBy(() -> recipeRepository.delete(null))
                .isInstanceOf(InvalidDataAccessApiUsageException.class);
        assertThatThrownBy(() -> recipeRepository.delete(999999999999999999L))
                .isInstanceOf(RecipeNotFoundException.class);
    }

    @Test
    void getByUser() {
//        TODO Pay attention to hibernate query because n+1 problem here.
        var found = recipeRepository.getByUser(recipeToByUser1.getUsername());
        assertThat(found).hasSize(3)
                .containsAll(List.of(recipeToByUser1, recipeToByUser2, recipeToByUser3));
    }
}
