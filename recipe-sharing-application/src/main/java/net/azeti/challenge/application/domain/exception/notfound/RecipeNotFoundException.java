package net.azeti.challenge.application.domain.exception.notfound;

public class RecipeNotFoundException extends NotFoundException {

    public RecipeNotFoundException(Long recipeId) {
        super(String.format("Recipe not found by recipeId = %s", recipeId));
    }
}