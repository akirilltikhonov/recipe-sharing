package net.azeti.challenge.application.integrationtest.layer.jpa.test.repository;

import net.azeti.challenge.application.domain.enums.Unit;
import net.azeti.challenge.application.infra.jpa.repository.RecipeJpaRepository;
import net.azeti.challenge.application.integrationtest.generator.IngredientEntityGenerator;
import net.azeti.challenge.application.integrationtest.generator.RecipeEntityGenerator;
import net.azeti.challenge.application.integrationtest.generator.pattern.IngredientPattern;
import net.azeti.challenge.application.integrationtest.generator.pattern.RecipePattern;
import net.azeti.challenge.application.integrationtest.layer.jpa.ApplicationJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RecipeJpaRepositoryTest extends ApplicationJpaTest {

    private final RecipeEntityGenerator recipeEntityGenerator = new RecipeEntityGenerator();
    private final IngredientEntityGenerator ingredientEntityGenerator = new IngredientEntityGenerator();
    @Autowired
    private RecipeJpaRepository recipeJpaRepository;
    private Long recipeId1;
    private Long recipeId2;
    private RecipePattern pattern2;
    private Long recipeWithIngredientsId;
    private IngredientPattern ingredientPattern;
    private Long recipeIdToRemoveIngredients;

    @PostConstruct
    void setUp() {
        recipeId1 = recipeJpaRepository.save(recipeEntityGenerator.nextRecipeEntity()).getRecipeId();

        pattern2 = RecipePattern.builder()
                .title(UUID.randomUUID().toString())
                .username(UUID.randomUUID().toString())
                .description(UUID.randomUUID().toString())
                .instructions(UUID.randomUUID().toString())
                .servings(4)
                .build();
        recipeId2 = recipeJpaRepository.save(recipeEntityGenerator.nextRecipeEntity(pattern2)).getRecipeId();

        ingredientPattern = IngredientPattern.builder()
                .value(1.25f)
                .unit(Unit.L)
                .type("milk")
                .build();
        var recipeWithIngredient = recipeEntityGenerator.nextRecipeEntity();
        recipeWithIngredient.addIngredient(ingredientEntityGenerator.nextIngredientEntity(ingredientPattern));
        recipeWithIngredient.addIngredient(ingredientEntityGenerator.nextIngredientEntity(ingredientPattern));
        recipeWithIngredient.addIngredient(ingredientEntityGenerator.nextIngredientEntity(ingredientPattern));
        recipeWithIngredient = recipeJpaRepository.save(recipeWithIngredient);
        recipeWithIngredientsId = recipeWithIngredient.getRecipeId();

        var recipeToRemove = recipeEntityGenerator.nextRecipeEntity();
        recipeToRemove.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeToRemove.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeToRemove.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipeIdToRemoveIngredients = recipeJpaRepository.save(recipeToRemove).getRecipeId();
    }

    @Test
    void findById() {
        assertThat(recipeJpaRepository.findById(recipeId1).orElseThrow().getRecipeId())
                .isEqualTo(recipeId1);
    }

    @Test
    void findByIdWithPattern() {
        var found = recipeJpaRepository.findById(recipeId2).orElseThrow();
        assertThat(found.getRecipeId())
                .isEqualTo(recipeId2);
        assertThat(pattern2)
                .usingRecursiveComparison()
                .isEqualTo(found);
    }

    @Test
    @Transactional
    void findByIdRecipeWithIngredients() {
        var found = recipeJpaRepository.findById(recipeWithIngredientsId).orElseThrow();
        assertThat(found.getRecipeId())
                .isEqualTo(recipeWithIngredientsId);
        assertThat(found.getIngredients().size()).isEqualTo(3);
        found.getIngredients().forEach(i -> {
            assertThat(i.getIngredientId()).isNotNull();
            assertThat(i.getRecipe()).isEqualTo(found);
            assertThat(ingredientPattern)
                    .usingRecursiveComparison()
                    .isEqualTo(i);
        });
    }

    @Test
    @Transactional
    void saveRecipeWithIngredients() {
        var recipe = recipeEntityGenerator.nextRecipeEntity();
        recipe.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipe.addIngredient(ingredientEntityGenerator.nextIngredientEntity());
        recipe.addIngredient(ingredientEntityGenerator.nextIngredientEntity());

        var recipeAfterSave = recipeJpaRepository.save(recipe);
        assertThat(recipeAfterSave.getIngredients().size()).isEqualTo(3);
        recipeAfterSave.getIngredients().forEach(i -> {
            assertThat(i.getIngredientId()).isNotNull();
        });
    }

    @Test
    @Transactional
    void deleteIngredients() {
        var found = recipeJpaRepository.findById(recipeIdToRemoveIngredients).orElseThrow();
        assertThat(found.getIngredients().size()).isEqualTo(3);
        found.removeIngredient(found.getIngredients().get(0));
        found.removeIngredient(found.getIngredients().get(1));

        var foundAfterRemove = recipeJpaRepository.findById(recipeIdToRemoveIngredients).orElseThrow();
        assertThat(foundAfterRemove.getIngredients().size()).isEqualTo(1);
    }
}
