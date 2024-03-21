package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Role;
import com.catering_app.Catering_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,UUID> {

    Optional<User>findByUsername(String username);
    Optional<User>findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User findByRole(Role role);
    List<User> findByActive(boolean active);


}
