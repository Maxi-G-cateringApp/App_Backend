package com.catering_app.Catering_app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private Integer id;
    private String name;
    private String type;
    private String filePath;
    @OneToOne
    @JoinColumn(name = "user_id")
    User user;
}
