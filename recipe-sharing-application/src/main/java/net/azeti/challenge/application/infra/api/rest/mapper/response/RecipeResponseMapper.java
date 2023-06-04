package net.azeti.challenge.application.infra.api.rest.mapper.response;

import net.azeti.challenge.api.dto.RecipeDto;
import net.azeti.challenge.application.domain.Recipe;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.ERROR
        , unmappedSourcePolicy = ReportingPolicy.ERROR
        , injectionStrategy = InjectionStrategy.CONSTRUCTOR
        , uses = IngredientResponseMapper.class
)
public interface RecipeResponseMapper {

    RecipeDto toRecipeDto(Recipe recipe);

    List<RecipeDto> toRecipeDtos(List<Recipe> recipes);
}
