package com.br.personniMoveis.restHandler;

import com.br.personniMoveis.exception.BadRequestException;
import com.br.personniMoveis.exception.BadRequestExceptionDetails;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestExceptions(BadRequestException badRequestException) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .title("Bad request exception, verifique a documentação da API.")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details(badRequestException.getMessage())
                        .appMessage("Exceção gerada na classe: ".concat(badRequestException.getClass().getName()))
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST);
    }
}
