package net.azeti.challenge.application.infra.api.rest.controller;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.api.dto.authentification.LoginDto;
import net.azeti.challenge.api.dto.authentification.RegistrationDto;
import net.azeti.challenge.api.dto.authentification.RegistrationResultDto;
import net.azeti.challenge.api.dto.authentification.TokenDto;
import net.azeti.challenge.application.app.service.UserManagement;
import net.azeti.challenge.application.infra.api.rest.mapper.AuthenticationMapper;
import net.azeti.challenge.client.AuthenticationControllerApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe-sharing/authentication")
public class AuthenticationController implements AuthenticationControllerApi {

    private final UserManagement userManagement;
    private final AuthenticationMapper authenticationMapper;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResultDto> register(@RequestBody @Valid RegistrationDto registrationDto) {
        var registration = authenticationMapper.toRegistration(registrationDto);
        var registrationResult = userManagement.register(registration);
        return ResponseEntity.ok(authenticationMapper.toRegistrationResultDto(registrationResult));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginDto loginDto) {
        var login = authenticationMapper.toLogin(loginDto);
        var token = userManagement.login(login);
        return ResponseEntity.ok(authenticationMapper.toTokenDto(token));
    }
}