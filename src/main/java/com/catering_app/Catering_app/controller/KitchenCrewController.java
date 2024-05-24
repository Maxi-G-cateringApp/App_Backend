package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.dto.team.KitchenCrewEmpDto;
import com.catering_app.Catering_app.dto.team.TeamDto;
import com.catering_app.Catering_app.model.teams.KitchenCrew;
import com.catering_app.Catering_app.model.teams.KitchenCrewEmployees;
import com.catering_app.Catering_app.service.teamServices.kitchenCrew.KitchenCrewService;
import com.catering_app.Catering_app.service.teamServices.kitchenCrewEmployees.KitchenCrewEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KitchenCrewController {

    @Autowired
    private KitchenCrewService kitchenCrewService;
    @Autowired
    private KitchenCrewEmployeeService kitchenCrewEmployeeService;

    @PostMapping("/add/kitchenCrew_team")
    public ResponseEntity<KitchenCrew> addTeam(@RequestBody TeamDto teamDto) {
        return ResponseEntity.ok(kitchenCrewService.addKitchenCrewTeam(teamDto));
    }

    @PostMapping("/add/kitchenCrew_impl")
    public ResponseEntity<?> addKitchenCrewEmployees(@RequestBody KitchenCrewEmpDto kitchenCrewEmpDto) {
        kitchenCrewEmployeeService.addKitchenCrewEmployees(kitchenCrewEmpDto);
        return ResponseEntity.ok(new ResponseDto(true, "success"));

    }

    @GetMapping("/kitchenCrew_teams")
    public ResponseEntity<List<KitchenCrew>> getAllKitchenCrewTeams() {
        return ResponseEntity.ok(kitchenCrewService.getAllSKitchenCrewTeam());
    }

    @GetMapping("/kitchenCrew_emp")
    public ResponseEntity<List<KitchenCrewEmployees>> getAllKitchenCrewEmployees() {
        return ResponseEntity.ok(kitchenCrewEmployeeService.getAllKitchenCrewEmployees());
    }

    @DeleteMapping("/delete-kitchen_crew")
    public ResponseEntity<?>deleteDecorationTeam(@RequestParam Integer id){
        kitchenCrewService.deleteKitchenCrewById(id);
        return ResponseEntity.ok(new ResponseDto(true,"deleted"));
    }

    @GetMapping("/get/kitchen-team-members")
    public ResponseEntity<?>getKitchenCrewTeamMembersByTeamId(@RequestParam Integer id){
        return ResponseEntity.ok(kitchenCrewEmployeeService.getAllKitchenCrewsByTeamId(id));
    }

}
