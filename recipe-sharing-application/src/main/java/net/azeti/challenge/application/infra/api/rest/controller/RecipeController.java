package net.azeti.challenge.application.infra.api.rest.controller;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.api.dto.CreateRecipeDto;
import net.azeti.challenge.api.dto.RecipeDto;
import net.azeti.challenge.api.dto.UpdateRecipeDto;
import net.azeti.challenge.application.app.service.RecipeManagement;
import net.azeti.challenge.application.infra.api.rest.mapper.request.RecipeRequestMapper;
import net.azeti.challenge.application.infra.api.rest.mapper.response.RecipeResponseMapper;
import net.azeti.challenge.client.RecipeControllerApi;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe-sharing/recipes")
public class RecipeController implements RecipeControllerApi {

    private final RecipeRequestMapper recipeRequestMapper;
    private final RecipeManagement recipeManagement;
    private final RecipeResponseMapper recipeResponseMapper;

    @PostMapping(value = "/create")
    public ResponseEntity<RecipeDto> create(@RequestBody @Valid CreateRecipeDto createRecipeDto) {
        var recipe = recipeRequestMapper.toRecipe(createRecipeDto);
        recipe = recipeManagement.create(recipe);
        return ResponseEntity.ok(recipeResponseMapper.toRecipeDto(recipe));
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeDto> getById(@PathVariable @NotNull Long recipeId) {
        var recipe = recipeManagement.getById(recipeId).orElseThrow();
        return ResponseEntity.ok(recipeResponseMapper.toRecipeDto(recipe));
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<RecipeDto> update(@PathVariable @NotNull Long recipeId, @RequestBody @Valid UpdateRecipeDto updateRecipeDto) {
        var recipe = recipeRequestMapper.toRecipe(updateRecipeDto);
        var updatedRecipe = recipeManagement.update(recipeId, recipe);
        return ResponseEntity.ok(recipeResponseMapper.toRecipeDto(updatedRecipe));
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<RecipeDto> delete(@PathVariable @NotNull Long recipeId) {
        var deletedRecipe = recipeManagement.delete(recipeId);
        return ResponseEntity.ok(recipeResponseMapper.toRecipeDto(deletedRecipe));
    }

    @GetMapping("/search/username/{username}")
    public ResponseEntity<List<RecipeDto>> getByUser(@PathVariable @NotNull String username) {
        var recipes = recipeManagement.getByUser(username);
        return ResponseEntity.ok(recipeResponseMapper.toRecipeDtos(recipes));
    }
}
