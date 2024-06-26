package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.teams.ServingTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServingTeamRepository extends JpaRepository<ServingTeam,Integer> {

    ServingTeam findByTeamName(String teamName);

}
