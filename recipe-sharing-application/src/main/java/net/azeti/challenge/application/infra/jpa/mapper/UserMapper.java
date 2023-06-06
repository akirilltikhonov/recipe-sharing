package net.azeti.challenge.application.infra.jpa.mapper;

import net.azeti.challenge.application.domain.User;
import net.azeti.challenge.application.infra.jpa.entity.UserEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring"
        , unmappedTargetPolicy = ReportingPolicy.ERROR
        , unmappedSourcePolicy = ReportingPolicy.ERROR
)
public interface UserMapper {

    @BeanMapping(ignoreUnmappedSourceProperties = {"userId"})
    User toUser(UserEntity userEntity);
}
