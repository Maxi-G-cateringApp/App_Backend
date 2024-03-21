package com.catering_app.Catering_app.service.authService;

import com.catering_app.Catering_app.dto.*;
import com.catering_app.Catering_app.model.AuthenticationResponse;
import com.catering_app.Catering_app.model.User;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public interface AuthenticationService {

    User register(UserRegisterDto registrationRequest) throws MessagingException;
    AuthenticationResponse authenticate(UserLoginDto userRequest);
    boolean verifyAccount(OtpDto otpDto);
    String regenerateOtp(String email, String otp) throws MessagingException;
    Optional<User> getUserById(UUID userId);
}
