package test.southsystem.desafiobackvotos.rest.advice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import test.southsystem.desafiobackvotos.model.exception.AgendaExpiredException;
import test.southsystem.desafiobackvotos.model.exception.CpfInvalidException;
import test.southsystem.desafiobackvotos.model.exception.NotFoundException;
import test.southsystem.desafiobackvotos.model.exception.UserAlreadyVotedException;
import test.southsystem.desafiobackvotos.model.exception.UserNotAllowedToVotedException;

/**
 * Classe responsavel por gerenciar as excecoes
 */
@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(value = UserNotAllowedToVotedException.class)
    public ResponseEntity<Map<String, Object>> handler(UserNotAllowedToVotedException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CpfInvalidException.class)
    public ResponseEntity<Map<String, Object>> handler(CpfInvalidException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AgendaExpiredException.class)
    public ResponseEntity<Map<String, Object>> handler(AgendaExpiredException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handler(NotFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserAlreadyVotedException.class)
    public ResponseEntity<Map<String, Object>> handler(UserAlreadyVotedException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    /**
     *
     * Gerencia as validacoes do javax.validation
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handler(MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());

        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        final List<String> validations = new ArrayList<>();

        for (FieldError f : fieldErrors) {
            validations.add(f.getDefaultMessage());
        }

        body.put("message", validations);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
