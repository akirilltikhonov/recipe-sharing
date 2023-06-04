package net.azeti.challenge.application.infra.api.rest.mapper.request;

import net.azeti.challenge.api.dto.CreateIngredientDto;
import net.azeti.challenge.application.domain.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.ERROR
        , unmappedSourcePolicy = ReportingPolicy.ERROR
)
public interface IngredientRequestMapper {

    @Mapping(target = "ingredientId", ignore = true)
    Ingredient toIngredient(CreateIngredientDto createIngredientDto);
}
