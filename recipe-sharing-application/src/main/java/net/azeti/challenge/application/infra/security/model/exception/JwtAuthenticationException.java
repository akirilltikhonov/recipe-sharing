package net.azeti.challenge.application.infra.security.model.exception;

import lombok.Getter;
import net.azeti.challenge.application.domain.exception.DomainException;
import org.springframework.http.HttpStatus;

@Getter
public class JwtAuthenticationException extends DomainException {

    private final HttpStatus httpStatus;

    public JwtAuthenticationException(String msg) {
        super(msg, HttpStatus.UNAUTHORIZED);
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }

    public JwtAuthenticationException(String msg, HttpStatus httpStatus) {
        super(msg, httpStatus);
        this.httpStatus = httpStatus;
    }
}