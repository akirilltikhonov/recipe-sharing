package net.azeti.challenge.application.infra.api.rest.mapper.request;

import net.azeti.challenge.api.dto.CreateRecipeDto;
import net.azeti.challenge.api.dto.RecipeFilterDto;
import net.azeti.challenge.api.dto.UpdateRecipeDto;
import net.azeti.challenge.application.app.port.gateway.TokenProviderGateway;
import net.azeti.challenge.application.app.port.gateway.mapping.GetUsernameTokenProviderGateway;
import net.azeti.challenge.application.domain.Recipe;
import net.azeti.challenge.application.domain.filter.RecipeFilter;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.ERROR
        , unmappedSourcePolicy = ReportingPolicy.ERROR
        , injectionStrategy = InjectionStrategy.CONSTRUCTOR
        , uses = {
        TokenProviderGateway.class,
        IngredientRequestMapper.class,
}
)
public interface RecipeRequestMapper {

    @Mapping(target = "recipeId", ignore = true)
    @Mapping(target = "username", source = "accessToken", qualifiedBy = GetUsernameTokenProviderGateway.class)
    @BeanMapping(ignoreUnmappedSourceProperties = {"empty", "bytes", "blank"})
    Recipe toRecipe(CreateRecipeDto createRecipeDto, String accessToken);

    @Mapping(target = "recipeId", ignore = true)
    Recipe toRecipe(UpdateRecipeDto updateRecipeDto);

    RecipeFilter toRecipeFilter(RecipeFilterDto recipeFilterDto);
}