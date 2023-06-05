package net.azeti.challenge.application.infra.api.rest.mapper.request;

import net.azeti.challenge.api.dto.CreateRecipeDto;
import net.azeti.challenge.api.dto.RecipeFilterDto;
import net.azeti.challenge.api.dto.UpdateRecipeDto;
import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.domain.filter.RecipeFilter;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.ERROR
        , unmappedSourcePolicy = ReportingPolicy.ERROR
        , injectionStrategy = InjectionStrategy.CONSTRUCTOR
        , uses = IngredientRequestMapper.class
)
public interface RecipeRequestMapper {

    @Mapping(target = "recipeId", ignore = true)
    Recipe toRecipe(CreateRecipeDto createRecipeDto);

    @Mapping(target = "recipeId", ignore = true)
    Recipe toRecipe(UpdateRecipeDto updateRecipeDto);

    RecipeFilter toRecipeFilter(RecipeFilterDto createRecipeDto);
}