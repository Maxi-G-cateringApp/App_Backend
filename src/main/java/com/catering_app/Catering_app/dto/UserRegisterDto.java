package com.catering_app.Catering_app.dto;

import com.catering_app.Catering_app.model.Role;
import lombok.Data;

@Data
public class UserRegisterDto {

    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
    private Role role;
}
