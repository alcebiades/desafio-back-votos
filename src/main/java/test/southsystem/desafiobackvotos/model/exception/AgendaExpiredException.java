package test.southsystem.desafiobackvotos.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AgendaExpiredException extends RuntimeException {

    public AgendaExpiredException(String message) {
        super(message);
    }
}
