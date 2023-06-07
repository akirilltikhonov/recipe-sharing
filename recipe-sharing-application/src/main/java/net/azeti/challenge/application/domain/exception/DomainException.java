package net.azeti.challenge.application.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class DomainException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    public DomainException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}