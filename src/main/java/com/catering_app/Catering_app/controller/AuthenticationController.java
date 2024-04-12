package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.*;
import com.catering_app.Catering_app.model.AuthenticationResponse;
import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.repository.UserRepository;
import com.catering_app.Catering_app.service.authService.AuthenticationService;
import com.catering_app.Catering_app.service.authService.AuthenticationServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;
import java.util.UUID;

@RestController
public class AuthenticationController {


    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;



    public AuthenticationController(AuthenticationServiceImpl authenticationService, UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto userRequest) throws MessagingException {
        if (userRepository.existsByEmail(userRequest.getEmail()) ||
                userRepository.existsByUserName(userRequest.getUserName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(" Email or Username Exist");
        }
        return ResponseEntity.ok(authenticationService.register(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserLoginDto userRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(userRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RefreshToken refreshToken) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }

    @PostMapping("/verify-account")
    public ResponseEntity<ResponseDto> verifyAccount(@RequestBody OtpDto otpDto) {
        boolean verificationSuccess = authenticationService.verifyAccount(otpDto);
        ResponseDto response;
        if (verificationSuccess) {
            response = new ResponseDto(true, "Verification successful");
        } else {
            response = new ResponseDto(false, "Invalid OTP");
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestBody OtpDto otpDto) throws MessagingException {
        return ResponseEntity.ok(authenticationService.regenerateOtp(otpDto.getEmail(), otpDto.getOtp()));
    }


    @GetMapping("/get-user")
    public ResponseEntity<User> getUserById(@RequestParam UUID userId) {
        Optional<User> optionalUser = authenticationService.getUserById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/google/login")
    public ResponseEntity<AuthenticationResponse> signInWithGoogle(@RequestParam("token") String token) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok(authenticationService.googleSignIn(token));
    }

}
