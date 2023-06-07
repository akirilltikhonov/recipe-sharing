package net.azeti.challenge.application.infra.api.rest.mapper;

import net.azeti.challenge.api.dto.authentification.LoginDto;
import net.azeti.challenge.api.dto.authentification.RegistrationDto;
import net.azeti.challenge.api.dto.authentification.RegistrationResultDto;
import net.azeti.challenge.api.dto.authentification.TokenDto;
import net.azeti.challenge.application.domain.authentification.Login;
import net.azeti.challenge.application.domain.authentification.Registration;
import net.azeti.challenge.application.domain.authentification.RegistrationResult;
import net.azeti.challenge.application.domain.authentification.Token;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.ERROR
        , unmappedSourcePolicy = ReportingPolicy.ERROR
)
public interface AuthenticationMapper {

    Registration toRegistration(RegistrationDto registrationDto);

    RegistrationResultDto toRegistrationResultDto(RegistrationResult registrationResult);

    Login toLogin(LoginDto loginDto);

    TokenDto toTokenDto(Token token);
}
