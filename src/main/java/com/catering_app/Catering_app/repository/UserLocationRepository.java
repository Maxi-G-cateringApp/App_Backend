package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation,Integer> {


}
