package com.br.personniMoveis.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RequestExceptionDetails {

    private String title;
    private int status;
    private String details;
    private String appMessage;
    private LocalDateTime timestamp;
}
