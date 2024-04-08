package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.teams.DecorationTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecorationTeamRepository extends JpaRepository<DecorationTeam,Integer> {

}
