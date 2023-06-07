package net.azeti.challenge.application.domain.exception.notfound;

import net.azeti.challenge.application.domain.exception.DomainException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends DomainException {

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}