package net.azeti.challenge.application.infra.jpa.mapper;

import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.infra.jpa.entity.RecipeEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.ERROR
        , unmappedSourcePolicy = ReportingPolicy.ERROR
        , injectionStrategy = InjectionStrategy.CONSTRUCTOR
        , uses = IngredientMapper.class
)
@DecoratedWith(RecipeDecoratorMapper.class)
public interface RecipeMapper {

    RecipeEntity toRecipeEntity(Recipe recipe);

    Recipe toRecipe(RecipeEntity recipeEntity);
}
