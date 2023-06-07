package net.azeti.challenge.application.infra.jpa.mapper;

import net.azeti.challenge.application.domain.authentification.Registration;
import net.azeti.challenge.application.infra.jpa.entity.UserEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
        componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.ERROR
        , unmappedSourcePolicy = ReportingPolicy.ERROR
)
public abstract class RegistrationMapper {

    @Autowired
    protected PasswordEncoder bCryptPasswordEncoder;

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "password", expression = "java(bCryptPasswordEncoder.encode(registration.getPassword()))")
    @BeanMapping(ignoreUnmappedSourceProperties = {"password"})
    public abstract UserEntity toUserEntity(Registration registration);
}
