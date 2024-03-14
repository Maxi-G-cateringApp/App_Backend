package com.catering_app.Catering_app.service.authService;

import com.catering_app.Catering_app.dto.OtpDto;
import com.catering_app.Catering_app.dto.UserLoginDto;
import com.catering_app.Catering_app.dto.UserRegisterDto;
import com.catering_app.Catering_app.model.AuthenticationResponse;
import com.catering_app.Catering_app.model.User;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    public User register(UserRegisterDto registrationRequest) throws MessagingException;
    public AuthenticationResponse authenticate(UserLoginDto userRequest);
    boolean verifyAccount(OtpDto otpDto);
    String regenerateOtp(String email, String otp) throws MessagingException;

}
