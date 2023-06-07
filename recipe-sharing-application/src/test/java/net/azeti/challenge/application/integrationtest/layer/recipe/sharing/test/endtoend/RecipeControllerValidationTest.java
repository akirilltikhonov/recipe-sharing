package net.azeti.challenge.application.integrationtest.layer.recipe.sharing.test.endtoend;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.azeti.challenge.api.dto.CreateRecipeDto;
import net.azeti.challenge.application.infra.api.rest.controller.RecipeController;
import net.azeti.challenge.application.integrationtest.layer.recipe.sharing.ApplicationTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RecipeControllerValidationTest extends ApplicationTest {

    @Autowired
    private RecipeController recipeController;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${access-token.non-expiring}")
    private String accessToken;

    static Stream<File> isNotValidCreate() throws IOException {
        return Arrays.stream(Objects.requireNonNull(
                new ClassPathResource("json/create/notValid").getFile().listFiles()));
    }

    static Stream<File> isValidCreate() throws IOException {
        return Arrays.stream(Objects.requireNonNull(
                new ClassPathResource("json/create/valid").getFile().listFiles()));
    }

    @ParameterizedTest
    @MethodSource("isNotValidCreate")
    @WithMockUser(authorities={"WRITE"})
    void isNotValidCreate(File file) throws IOException {
        var request = objectMapper.readValue(file, CreateRecipeDto.class);
        assertThatThrownBy(() -> recipeController.create(request, accessToken))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @ParameterizedTest
    @MethodSource("isValidCreate")
    @WithMockUser(authorities={"WRITE"})
    void isValidCreate(File file) throws IOException {
        var request = objectMapper.readValue(file, CreateRecipeDto.class);
        assertThat(recipeController.create(request, accessToken).getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }
}