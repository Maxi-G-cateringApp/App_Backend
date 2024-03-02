package com.catering_app.Catering_app.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {

    private String token;
    private User user;
}
