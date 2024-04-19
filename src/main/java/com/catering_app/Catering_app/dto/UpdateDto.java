package com.catering_app.Catering_app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateDto {

    private UUID userId;
    private String name;
    private String phoneNumber;
}
