package com.br.personniMoveis.exception;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestExceptionDetails {

    private String title;
    private int status;
    private String details;
    private String appMessage;
    private LocalDateTime timestamp;
}
