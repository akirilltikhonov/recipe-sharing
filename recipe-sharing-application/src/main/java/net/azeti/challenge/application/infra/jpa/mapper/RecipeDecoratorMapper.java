package net.azeti.challenge.application.infra.jpa.mapper;

import lombok.Setter;
import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.infra.jpa.entity.RecipeEntity;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
public abstract class RecipeDecoratorMapper implements RecipeMapper {

    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public RecipeEntity toRecipeEntity(Recipe recipe) {
        var recipeEntity = recipeMapper.toRecipeEntity(recipe);
        if (recipeEntity != null) {
            recipeEntity.linkIngredients(recipeEntity.getIngredients());
        }
        return recipeEntity;
    }
}