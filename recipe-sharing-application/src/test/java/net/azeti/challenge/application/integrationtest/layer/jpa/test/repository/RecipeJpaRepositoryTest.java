package net.azeti.challenge.application.integrationtest.layer.jpa.test.repository;

import net.azeti.challenge.application.infra.jpa.repository.RecipeJpaRepository;
import net.azeti.challenge.application.integrationtest.generator.RecipeEntityGenerator;
import net.azeti.challenge.application.integrationtest.generator.pattern.RecipePattern;
import net.azeti.challenge.application.integrationtest.layer.jpa.ApplicationJpaTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class RecipeJpaRepositoryTest extends ApplicationJpaTest {

    private final RecipeEntityGenerator recipeEntityGenerator = new RecipeEntityGenerator();
    @Autowired
    private RecipeJpaRepository recipeJpaRepository;
    private Long recipeId1;
    private Long recipeId2;
    private RecipePattern pattern2;

    @BeforeEach
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
}
