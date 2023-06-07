package net.azeti.challenge.application.integrationtest.layer.recipe.sharing.test.endtoend;

import net.azeti.challenge.api.dto.CreateIngredientDto;
import net.azeti.challenge.api.dto.CreateRecipeDto;
import net.azeti.challenge.api.dto.RecipeDto;
import net.azeti.challenge.api.dto.authentification.LoginDto;
import net.azeti.challenge.api.dto.authentification.RegistrationDto;
import net.azeti.challenge.api.dto.authentification.RegistrationResultDto;
import net.azeti.challenge.api.dto.authentification.TokenDto;
import net.azeti.challenge.api.enums.Unit;
import net.azeti.challenge.application.integrationtest.layer.recipe.sharing.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticationControllerEndToEndTest extends ApplicationTest {
    private static final String BASE_PATH_AUTH = "/recipe-sharing/authentication/";
    private static final String BASE_PATH = "/recipe-sharing/recipes/";
    private final String header = HttpHeaders.AUTHORIZATION;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void registerLoginCreate() {
        String username = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        var registrationDto = RegistrationDto.builder()
                .username(username)
                .password(password)
                .email(UUID.randomUUID() + "@mail.com")
                .build();
        var registerResponse = testRestTemplate
                .postForEntity(BASE_PATH_AUTH + "register", registrationDto, RegistrationResultDto.class);
        assertThat(registerResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(registerResponse.getBody())
                .isEqualTo(RegistrationResultDto.builder()
                        .username(username)
                        .email(registrationDto.email())
                        .build());

        var loginDto = LoginDto.builder()
                .username(username)
                .password(password)
                .build();
        var loginResponse = testRestTemplate
                .postForEntity(BASE_PATH_AUTH + "login", loginDto, TokenDto.class);
        assertThat(loginResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        var token = loginResponse.getBody();
        assertThat(token).isNotNull();
        assertThat(token.accessToken()).isNotNull();

        var headers = new HttpHeaders();
        headers.add(header, token.accessToken());
        var body = CreateRecipeDto.builder()
                .title("Omelette")
                .description("best ever")
                .instructions("just add all")
                .servings(1)
                .ingredients(List.of(CreateIngredientDto.builder()
                        .value(1000f)
                        .unit(Unit.ML)
                        .type("milk")
                        .build()))
                .build();
        var httpEntity = new HttpEntity<>(body, headers);
        var createResponse = testRestTemplate
                .exchange(BASE_PATH + "create", HttpMethod.POST, httpEntity, RecipeDto.class);
        assertThat(createResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(createResponse.getBody()).isNotNull();
        assertThat(body)
                .usingRecursiveComparison()
                .isEqualTo(createResponse.getBody());
        assertThat(createResponse.getBody().username())
                .isEqualTo(username);
    }
}