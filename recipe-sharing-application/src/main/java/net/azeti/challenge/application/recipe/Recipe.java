package net.azeti.challenge.application.recipe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recipe {
    private Long id;
    private String title;
    private String username;
    private String description;
    //... rest of attributes.
}