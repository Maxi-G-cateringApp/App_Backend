package com.catering_app.Catering_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {

    private boolean status;
    private String message;
}
