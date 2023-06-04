package net.azeti.challenge.application.infra.api.rest.mapper.response;

import net.azeti.challenge.api.dto.IngredientDto;
import net.azeti.challenge.application.domain.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.ERROR
        , unmappedSourcePolicy = ReportingPolicy.ERROR
)
public interface IngredientResponseMapper {

    IngredientDto toIngredientDto(Ingredient ingredient);
}
