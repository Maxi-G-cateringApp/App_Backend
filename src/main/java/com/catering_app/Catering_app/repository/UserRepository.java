package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User>findByUsername(String username);
}
