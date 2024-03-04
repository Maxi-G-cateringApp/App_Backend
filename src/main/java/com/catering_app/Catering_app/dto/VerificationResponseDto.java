package com.catering_app.Catering_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerificationResponseDto {

    private boolean status;
    private String message;
}
