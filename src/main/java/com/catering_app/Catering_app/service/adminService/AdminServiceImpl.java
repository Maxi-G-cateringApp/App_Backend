package com.catering_app.Catering_app.service.adminService;

import com.catering_app.Catering_app.dto.PartnerDto;
import com.catering_app.Catering_app.model.Role;
import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.repository.UserRepository;
import com.catering_app.Catering_app.service.authService.AuthenticationService;
import com.catering_app.Catering_app.utilis.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public void setUserAsPartner(UUID userId){
        Optional<User> optionalUser = authenticationService.getUserById(userId);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setPartner(true);
            user.setRole(Role.PARTNER);
            System.out.println("Setting role: " + user.getRole());
            userRepository.save(user);
        }
    }

    @Override
    public List<User> getAllPartnerUsers() {
        return userRepository.findAll().stream().filter(user->user.getRole().equals(Role.USER)
                && user.isPartner()).toList();
    }

    @Override
    public void createPartner(PartnerDto partnerDto) {
        userRepository.save(User.builder()
                        .isPartner(true)
                        .email(partnerDto.getEmail())
                        .password(passwordEncoder.encode(partnerDto.getPassword()))
                        .active(true)
                        .name(partnerDto.getUserName())
                        .phoneNumber(partnerDto.getPhoneNumber())
                        .googleSignIn(false)
                        .otp(null)
                        .registerDateTime(LocalDateTime.now())
                        .role(Role.USER)
                .build());
    }
}
