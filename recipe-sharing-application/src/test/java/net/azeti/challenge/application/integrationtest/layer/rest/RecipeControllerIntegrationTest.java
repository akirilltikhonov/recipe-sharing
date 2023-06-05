package net.azeti.challenge.application.integrationtest.layer.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.azeti.challenge.api.dto.CreateIngredientDto;
import net.azeti.challenge.api.dto.CreateRecipeDto;
import net.azeti.challenge.api.dto.IngredientDto;
import net.azeti.challenge.api.dto.RecipeDto;
import net.azeti.challenge.api.dto.UpdateRecipeDto;
import net.azeti.challenge.api.enums.Unit;
import net.azeti.challenge.application.infra.api.rest.controller.RecipeController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {RecipeController.class})
class RecipeControllerIntegrationTest {
    private static final String BASE_PATH = "/recipe-sharing/recipes/";

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private RecipeController controller;

    @Test
    void create() throws Exception {
        var createRecipeDto = CreateRecipeDto.builder()
                .title("title")
                .username("username")
                .description("description")
                .instructions("instructions")
                .servings(1)
                .ingredients(List.of(CreateIngredientDto.builder()
                        .value(1000f)
                        .unit(Unit.ML)
                        .type("type")
                        .build()))
                .build();

        var recipeDto = RecipeDto.builder().build();
        doReturn(ResponseEntity.ok(recipeDto)).when(controller)
                .create(createRecipeDto);

        String jsonRequest = objectMapper.writeValueAsString(createRecipeDto);
        String expectedResponse = objectMapper.writeValueAsString(recipeDto);

        var response = mockMvc.perform(post(BASE_PATH + "create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertThat(response.getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void getById() throws Exception {
        var recipeDto = RecipeDto.builder().recipeId(999L).build();
        doReturn(ResponseEntity.ok(recipeDto)).when(controller)
                .getById(recipeDto.recipeId());

        String expectedResponse = objectMapper.writeValueAsString(recipeDto);

        var response = mockMvc.perform(get(BASE_PATH + recipeDto.recipeId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertThat(response.getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void update() throws Exception {
        Long recipeId = 999L;
        var updateRecipeDto = UpdateRecipeDto.builder()
                .title("title")
                .username("username")
                .description("description")
                .instructions("instructions")
                .servings(1)
                .ingredients(List.of(IngredientDto.builder()
                        .value(1000f)
                        .unit(Unit.ML)
                        .type("type")
                        .build()))
                .build();

        var recipeDto = RecipeDto.builder().recipeId(recipeId).build();
        doReturn(ResponseEntity.ok(recipeDto)).when(controller)
                .update(recipeId, updateRecipeDto);

        String jsonRequest = objectMapper.writeValueAsString(updateRecipeDto);
        String expectedResponse = objectMapper.writeValueAsString(recipeDto);

        var response = mockMvc.perform(put(BASE_PATH + recipeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertThat(response.getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void delete() throws Exception {
        Long recipeId = 999L;
        var recipeDto = RecipeDto.builder().recipeId(999L).build();
        doReturn(ResponseEntity.ok(recipeDto)).when(controller)
                .delete(recipeId);

        String expectedResponse = objectMapper.writeValueAsString(recipeDto);

        var response = mockMvc.perform(MockMvcRequestBuilders.delete(BASE_PATH + recipeDto.recipeId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertThat(response.getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void getByUser() throws Exception {
        String username = "username";
        var recipeDtos = List.of(RecipeDto.builder().username("username").build());
        doReturn(ResponseEntity.ok(recipeDtos)).when(controller)
                .getByUser(username);

        String expectedResponse = objectMapper.writeValueAsString(recipeDtos);

        var response = mockMvc.perform(get(BASE_PATH + "search/username/" + username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertThat(response.getContentAsString()).isEqualTo(expectedResponse);
    }
}