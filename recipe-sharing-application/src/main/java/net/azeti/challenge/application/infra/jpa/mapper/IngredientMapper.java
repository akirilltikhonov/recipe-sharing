package net.azeti.challenge.application.infra.jpa.mapper;

import net.azeti.challenge.application.domain.Ingredient;
import net.azeti.challenge.application.infra.jpa.entity.IngredientEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.ERROR
        , unmappedSourcePolicy = ReportingPolicy.ERROR
)
public interface IngredientMapper {

    @Mapping(target = "recipe", ignore = true)
    IngredientEntity toIngredientEntity(Ingredient ingredient);

    @BeanMapping(ignoreUnmappedSourceProperties = {"recipe"})
    Ingredient toIngredient(IngredientEntity ingredientEntity);
}
