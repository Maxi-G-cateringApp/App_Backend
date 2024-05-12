package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.PartnerDto;
import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.service.adminService.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/set-partner")
    public ResponseEntity<?>setUserAsPartner(@RequestParam UUID userId){
        adminService.setUserAsPartner(userId);
        return ResponseEntity.ok(new ResponseDto(true,"partner added"));
    }
    @GetMapping("/partner-users")
    public ResponseEntity<List<User>>getPartnerUsers(){
        return ResponseEntity.ok(adminService.getAllPartnerUsers());
    }

    @PostMapping("/create/partner")
    public ResponseEntity<?>createPartner(@RequestBody PartnerDto partnerDto){
        adminService.createPartner(partnerDto);
        return ResponseEntity.ok(new ResponseDto(true,"partner added"));

    }
}
