package com.br.personniMoveis.restHandler;

import com.br.personniMoveis.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<RequestExceptionDetails> handlerInternalServerErrorException(InternalServerErrorException ex) {
        return new ResponseEntity<>(
                RequestExceptionDetails.builder()
                        .title("Internal server error, verifique a documentação da API.")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .details(ex.getMessage())
                        .appMessage("Exceção gerada na classe: ".concat(ex.getClass().getName()))
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<RequestExceptionDetails> handlerBadRequestExceptions(BadRequestException ex) {
        return new ResponseEntity<>(
                RequestExceptionDetails.builder()
                        .title("Bad request exception, verifique a documentação da API.")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details(ex.getMessage())
                        .appMessage("Exceção gerada na classe: ".concat(ex.getClass().getName()))
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RequestExceptionDetails> handlerResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                RequestExceptionDetails.builder()
                        .title("Recurso não encontrado, verifique a documentação da API.")
                        .status(HttpStatus.NOT_FOUND.value())
                        .details(ex.getMessage())
                        .appMessage("Exceção gerada na classe: ".concat(ex.getClass().getName()))
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<RequestExceptionDetails> handlerResourceAlreadyExistsException(AlreadyExistsException ex) {
        return new ResponseEntity<>(
                RequestExceptionDetails.builder()
                        .title("Recurso já existe no local, verifique a documentação da API.")
                        .status(HttpStatus.CONFLICT.value())
                        .details(ex.getMessage())
                        .appMessage("Exceção gerada na classe: ".concat(ex.getClass().getName()))
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.CONFLICT);
    }
}
