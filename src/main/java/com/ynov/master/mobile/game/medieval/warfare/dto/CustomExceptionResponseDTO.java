package com.ynov.master.mobile.game.medieval.warfare.dto;

import com.ynov.master.mobile.game.medieval.warfare.exception.CustomException;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Data
public class CustomExceptionResponseDTO {

    private LocalDateTime date;
    private String error;
    private String cause;
    private int status;
    private String path;

    public CustomExceptionResponseDTO(CustomException e, HttpServletRequest req)
    {
        this.date = LocalDateTime.now();
        this.error = e.getHttpStatus().getReasonPhrase();
        this.cause = e.getMessage();
        this.status = e.getHttpStatus().value();
        this.path = req.getRequestURI();

    }

}
