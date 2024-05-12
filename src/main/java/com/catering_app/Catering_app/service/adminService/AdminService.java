package com.catering_app.Catering_app.service.adminService;

import com.catering_app.Catering_app.dto.PartnerDto;
import com.catering_app.Catering_app.model.User;

import java.util.List;
import java.util.UUID;

public interface AdminService {

    void setUserAsPartner(UUID userId);

    List<User> getAllPartnerUsers();

    void createPartner(PartnerDto partnerDto);

}


