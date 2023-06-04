package net.azeti.challenge.application.integrationtest.layer.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.azeti.challenge.api.dto.CreateRecipeDto;
import net.azeti.challenge.api.dto.RecipeDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {RecipeController.class})
class RecipeControllerIntegrationTest {
    private static final String BASE_PATH = "/recipe-sharing/recipes";

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private RecipeController controller;

    @Test
    void create() throws Exception {
        var createRecipeDto = CreateRecipeDto.builder().build();

        var recipeDto = RecipeDto.builder().build();
        doReturn(ResponseEntity.ok(recipeDto)).when(controller)
                .create(createRecipeDto);

        String jsonRequest = objectMapper.writeValueAsString(createRecipeDto);
        String expectedResponse = objectMapper.writeValueAsString(recipeDto);

        var response = mockMvc.perform(post(BASE_PATH + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertThat(response.getContentAsString()).isEqualTo(expectedResponse);
    }
}