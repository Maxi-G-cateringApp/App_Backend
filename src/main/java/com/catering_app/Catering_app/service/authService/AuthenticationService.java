package com.catering_app.Catering_app.service.authService;

import com.catering_app.Catering_app.dto.UserLoginDto;
import com.catering_app.Catering_app.dto.UserRegisterDto;
import com.catering_app.Catering_app.model.AuthenticationResponse;
import com.catering_app.Catering_app.model.User;

public interface AuthenticationService {

    public User register(UserRegisterDto registrationRequest);
    public AuthenticationResponse authenticate(UserLoginDto userRequest);
}
