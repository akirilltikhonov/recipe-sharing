package net.azeti.challenge.application.infra.jpa.mapper;

import net.azeti.challenge.application.domain.authentification.Registration;
import net.azeti.challenge.application.infra.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.ERROR
        , unmappedSourcePolicy = ReportingPolicy.ERROR
)
public interface RegistrationMapper {

    @Mapping(target = "userId", ignore = true)
    UserEntity toUserEntity(Registration registration);
}
