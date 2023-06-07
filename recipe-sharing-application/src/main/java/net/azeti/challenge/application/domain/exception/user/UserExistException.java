package net.azeti.challenge.application.domain.exception.user;

import net.azeti.challenge.application.domain.exception.DomainException;
import org.springframework.http.HttpStatus;

public class UserExistException extends DomainException {

    public UserExistException(String username, String email) {
        super(String.format("User with such username or email is existed: %s, %s", username, email), HttpStatus.BAD_REQUEST);
    }
}