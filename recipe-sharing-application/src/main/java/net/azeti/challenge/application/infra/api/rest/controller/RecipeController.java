package net.azeti.challenge.application.infra.api.rest.controller;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.api.dto.RecipeDto;
import net.azeti.challenge.api.dto.CreateRecipeDto;
import net.azeti.challenge.application.app.service.RecipeManagement;
import net.azeti.challenge.application.infra.api.rest.mapper.request.RecipeRequestMapper;
import net.azeti.challenge.application.infra.api.rest.mapper.response.RecipeResponseMapper;
import net.azeti.challenge.client.RecipeControllerApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


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
}
