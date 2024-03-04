//package com.catering_app.Catering_app.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "otp")
//@Data
//public class Otp {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "otp_id")
//    private Integer id;
//    private String otp;
//    private LocalDateTime otpGeneratedDateTime;
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    User user;
//}
