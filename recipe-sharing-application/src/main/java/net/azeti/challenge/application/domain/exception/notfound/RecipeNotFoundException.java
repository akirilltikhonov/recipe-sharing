package net.azeti.challenge.application.domain.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecipeNotFoundException extends NotFoundException {

    public RecipeNotFoundException(Long recipeId) {
        super(String.format("Recipe not found by recipeId = %s", recipeId));
    }
}