package net.azeti.challenge.application.infra.api.rest.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import net.azeti.challenge.api.dto.CreateRecipeDto;
import net.azeti.challenge.api.dto.RecipeDto;
import net.azeti.challenge.api.dto.RecipeFilterDto;
import net.azeti.challenge.api.dto.UpdateRecipeDto;
import net.azeti.challenge.application.app.service.RecipeManagement;
import net.azeti.challenge.application.app.service.RecipeSearch;
import net.azeti.challenge.application.domain.exception.notfound.RecipeNotFoundException;
import net.azeti.challenge.application.infra.api.rest.mapper.request.RecipeRequestMapper;
import net.azeti.challenge.application.infra.api.rest.mapper.response.RecipeResponseMapper;
import net.azeti.challenge.client.RecipeControllerApi;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe-sharing/recipes")
@SecurityRequirement(name = "Authorization")
public class RecipeController implements RecipeControllerApi {

    private final RecipeManagement recipeManagement;
    private final RecipeSearch recipeSearch;
    private final RecipeRequestMapper recipeRequestMapper;
    private final RecipeResponseMapper recipeResponseMapper;

    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('WRITE')")
    public ResponseEntity<RecipeDto> create(@RequestBody @Valid CreateRecipeDto createRecipeDto,
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken
    ) {
        var recipe = recipeRequestMapper.toRecipe(createRecipeDto, accessToken);
        recipe = recipeManagement.create(recipe);
        return ResponseEntity.ok(recipeResponseMapper.toRecipeDto(recipe));
    }

    @GetMapping("/{recipeId}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<RecipeDto> getById(@PathVariable @NotNull Long recipeId) {
        var recipe = recipeManagement.getById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(recipeId));
        return ResponseEntity.ok(recipeResponseMapper.toRecipeDto(recipe));
    }

    @PutMapping("/{recipeId}")
    @PreAuthorize("hasAuthority('WRITE')")
    public ResponseEntity<RecipeDto> update(@PathVariable @NotNull Long recipeId,
                                            @RequestBody @Valid UpdateRecipeDto updateRecipeDto,
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken
    ) {
        var recipe = recipeRequestMapper.toRecipe(updateRecipeDto, accessToken);
        var updatedRecipe = recipeManagement.update(recipeId, recipe);
        return ResponseEntity.ok(recipeResponseMapper.toRecipeDto(updatedRecipe));
    }

    @DeleteMapping("/{recipeId}")
    @PreAuthorize("hasAuthority('WRITE')")
    public ResponseEntity<RecipeDto> delete(@PathVariable @NotNull Long recipeId) {
        var deletedRecipe = recipeManagement.delete(recipeId);
        return ResponseEntity.ok(recipeResponseMapper.toRecipeDto(deletedRecipe));
    }

    @GetMapping("/search/username/{username}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<RecipeDto>> getByUser(@PathVariable @NotNull String username) {
        var recipes = recipeManagement.getByUser(username);
        return ResponseEntity.ok(recipeResponseMapper.toRecipeDtos(recipes));
    }

    @PostMapping(value = "/search/filter")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<RecipeDto>> findByFilter(@RequestBody @Valid RecipeFilterDto recipeFilterDto) {
        var filter = recipeRequestMapper.toRecipeFilter(recipeFilterDto);
        var recipes = recipeSearch.recipesByFilter(filter);
        return ResponseEntity.ok(recipeResponseMapper.toRecipeDtos(recipes));
    }
}
