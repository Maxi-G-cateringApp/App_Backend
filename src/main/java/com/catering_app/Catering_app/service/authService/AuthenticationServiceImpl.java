package com.catering_app.Catering_app.service.authService;

import com.catering_app.Catering_app.dto.OtpDto;
import com.catering_app.Catering_app.dto.UserLoginDto;
import com.catering_app.Catering_app.dto.UserRegisterDto;
import com.catering_app.Catering_app.model.AuthenticationResponse;
import com.catering_app.Catering_app.model.Role;
import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.repository.UserRepository;
import com.catering_app.Catering_app.service.jwtService.JwtService;
import com.catering_app.Catering_app.service.jwtService.JwtServiceImpl;
import com.catering_app.Catering_app.utilis.EmailUtil;
import com.catering_app.Catering_app.utilis.OtpUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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

        User user = new User();


            user.setFirstName(registrationRequest.getFirstName());
            user.setLastName(registrationRequest.getLastName());
            user.setUsername(registrationRequest.getUsername());
            user.setPhoneNumber(registrationRequest.getPhoneNumber());
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(Role.USER);
            String verificationOtp = otpUtils.generateOtp();
            emailUtil.sentOtpEmail(registrationRequest.getEmail(),verificationOtp);
            user.setOtp(verificationOtp);
            user.setOtpGeneratedDateTime(LocalDateTime.now());


            return userRepository.save(user);

    }

    public AuthenticationResponse authenticate(UserLoginDto userRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getUsername(),
                        userRequest.getPassword()
                )
        );
        User user = userRepository.findByUsername(userRequest.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token,user);
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
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));

        String reOtp = otpUtils.generateOtp();
        emailUtil.sentOtpEmail(email,otp);
        user.setOtp(reOtp);
        user.setOtpGeneratedDateTime(LocalDateTime.now());
        return "Resent";
    }

}
