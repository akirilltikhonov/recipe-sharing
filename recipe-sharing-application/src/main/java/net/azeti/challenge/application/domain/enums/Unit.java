package net.azeti.challenge.application.domain.enums;

import lombok.Getter;

public enum Unit {
    G("Gram"),
    KG("Kilogram"),
    ML("Milliliter"),
    L("Liter"),
    PC("Piece"),
    TSP("Teaspoon"),
    TBSP("Tablespoon"),
    PINCH("A dash");

    @Getter
    private final String comment;

    Unit(String label) {
        this.comment = label;
    }
}
