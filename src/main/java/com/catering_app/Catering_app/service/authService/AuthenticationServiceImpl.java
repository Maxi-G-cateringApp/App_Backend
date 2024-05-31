package com.catering_app.Catering_app.service.authService;

import com.catering_app.Catering_app.dto.*;
import com.catering_app.Catering_app.model.AuthenticationResponse;
import com.catering_app.Catering_app.model.Role;
import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.repository.UserRepository;
import com.catering_app.Catering_app.service.jwtService.JwtService;
import com.catering_app.Catering_app.service.jwtService.JwtServiceImpl;
import com.catering_app.Catering_app.utilis.EmailUtil;
import com.catering_app.Catering_app.utilis.OtpUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Value("${spring.security.oauth2.resource-server.opaque-token.client-id}")
    private String clientId;


    @Autowired
    OtpUtils otpUtils;
    @Autowired
    EmailUtil emailUtil;


    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtServiceImpl jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;

    }

    public User register(UserRegisterDto registrationRequest) throws MessagingException {

        User user = createNewUser(registrationRequest);
        String verificationOtp = otpUtils.generateOtp();
            emailUtil.sentOtpEmail(registrationRequest.getEmail(),verificationOtp);
            user.setOtp(verificationOtp);
            user.setOtpGeneratedDateTime(LocalDateTime.now());
            return userRepository.save(user);
    }

    private User createNewUser(UserRegisterDto registrationRequest) {
        User user = new User();
        user.setName(registrationRequest.getUserName());
        user.setPhoneNumber(registrationRequest.getPhoneNumber());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRole(Role.USER);
        user.setActive(false);
        user.setGoogleSignIn(false);
        user.setRegisterDateTime(LocalDateTime.now());
        return user;
    }

    public AuthenticationResponse authenticate(UserLoginDto userRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getEmail(),
                        userRequest.getPassword()
                )
        );
        User user = userRepository.findByEmail(userRequest.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
        return new AuthenticationResponse(token,user,refreshToken);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshToken refreshToken) {
        String email = jwtService.extractUserName(refreshToken.getToken());
        User user = userRepository.findByEmail(email).orElseThrow();
        if(jwtService.isValid(refreshToken.getToken(),user)){
            var jwt = jwtService.generateToken(user);
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setToken(jwt);
            authenticationResponse.setRefreshToken(refreshToken.getToken());
            return authenticationResponse;
        }
        return null;
    }

    @Override
    public boolean verifyAccount(OtpDto otpDto) {
        User user = userRepository.findByEmail(otpDto.getEmail()).orElseThrow(()->
                new RuntimeException("user not found"));
        if (user.getOtp().equals(otpDto.getOtp()) &&
                Duration.between(user.getOtpGeneratedDateTime(),
                        LocalDateTime.now()).getSeconds() < (10 * 60)){
            user.setActive(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public String regenerateOtp(String email, String otp) throws MessagingException {
        User user = userRepository.findByEmail(email).orElseThrow(()->
                new RuntimeException("user not found"));
        String reOtp = otpUtils.generateOtp();
        emailUtil.sentOtpEmail(email,otp);
        user.setOtp(reOtp);
        user.setOtpGeneratedDateTime(LocalDateTime.now());
        return "Resent";
    }

    @Override
    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);

    }


    public AuthenticationResponse googleSignIn(String token) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singleton(clientId))
                .build();
        GoogleIdToken idToken = verifier.verify(token);
        if(token != null){
            GoogleIdToken.Payload payload = idToken.getPayload();
            Optional<User> optionalUser = userRepository.findByEmail(payload.getEmail());
            if (optionalUser.isPresent()){
                User existingUser = optionalUser.get();
                String refreshToken = payload.getSubject();
                String gToken = jwtService.generateToken(existingUser);
                return new AuthenticationResponse(gToken,existingUser,refreshToken);
            }else{
                User user = userRepository.save(User.builder()
                        .active(payload.getEmailVerified())
                        .email(payload.getEmail())
                        .name((String) payload.get("given_name"))
                        .googleSignIn(true)
                        .role(Role.USER)
                        .build());
                String refreshToken = payload.getSubject();
                String gToken = jwtService.generateToken(user);
                return new AuthenticationResponse(gToken,user,refreshToken);
            }
        } else {
            System.out.println("Invalid ID token.");
        }
        return null;
    }

    @Override
    public User getAdmin() {
        return userRepository.findByRole(Role.ADMIN);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().
                filter(user-> user.getRole().equals(Role.USER)).toList();
    }

    public User updateUser(UUID userId,UpdateDto updateUser){
        System.out.println(userId+" update user");
        Optional<User>optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setName(updateUser.getName());
            user.setPhoneNumber(updateUser.getPhoneNumber());
            userRepository.save(user);
            return user;
        }else{
            throw new EntityNotFoundException();
        }
    }
}


