package net.azeti.challenge.application.integrationtest.generator.pattern;

import lombok.Builder;
import lombok.Value;
import net.azeti.challenge.application.domain.enums.Unit;

@Value
@Builder(toBuilder = true)
public class IngredientPattern {

    Float value;
    Unit unit;
    String type;
}
