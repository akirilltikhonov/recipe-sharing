package net.azeti.challenge.application.domain;

import lombok.Builder;
import lombok.Value;
import net.azeti.challenge.application.domain.enums.Unit;

@Value
@Builder(toBuilder = true)
public class Ingredient {

    Long ingredientId;
    Float value;
    Unit unit;
    String type;
}