package net.azeti.challenge.application.infra.api.rest.mapper;

import net.azeti.challenge.api.dto.authentification.LoginDto;
import net.azeti.challenge.api.dto.authentification.TokenDto;
import net.azeti.challenge.application.domain.authentification.Login;
import net.azeti.challenge.application.domain.authentification.Token;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.ERROR
        , unmappedSourcePolicy = ReportingPolicy.ERROR
)
public interface AuthenticationMapper {

    Login toLogin(LoginDto loginDto);

    TokenDto toTokenDto(Token token);
}
