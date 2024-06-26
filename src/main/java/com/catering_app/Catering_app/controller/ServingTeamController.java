package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.dto.team.ServingEmpDto;
import com.catering_app.Catering_app.dto.team.TeamDto;
import com.catering_app.Catering_app.model.teams.ServingEmployees;
import com.catering_app.Catering_app.model.teams.ServingTeam;
import com.catering_app.Catering_app.service.teamServices.servingEmployees.ServingEmployeesService;
import com.catering_app.Catering_app.service.teamServices.servingTeam.ServingTeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServingTeamController {

    private final ServingTeamService servingTeamService;
    private final ServingEmployeesService servingEmployeesService;

    public ServingTeamController(ServingTeamService servingTeamService, ServingEmployeesService servingEmployeesService) {
        this.servingTeamService = servingTeamService;
        this.servingEmployeesService = servingEmployeesService;
    }


    @PostMapping("/add/serv_team")
    public ResponseEntity<ServingTeam>addTeam(@RequestBody TeamDto teamDto){
        return ResponseEntity.ok(servingTeamService.addServingTeam(teamDto));
    }

    @GetMapping("/serving_teams")
    public ResponseEntity<List<ServingTeam>> getAllServingTeams(){
        return ResponseEntity.ok(servingTeamService.getAllServingTeam());
    }
    @GetMapping("/get/team-members")
    public ResponseEntity<?>getServingTeamMembersByTeamId(@RequestParam Integer id){
        return ResponseEntity.ok(servingEmployeesService.getAllServingTeamByTeamId(id));
    }
    @GetMapping("/serving_emp")
    public ResponseEntity<List<ServingEmployees>> getAllServingEmployees(){
        return ResponseEntity.ok(servingEmployeesService.getAllServingEmployees());
    }
    @PutMapping("/inactive/member/{empId}")
    public ResponseEntity<?> removeTeamMember(@PathVariable Integer empId){
        servingEmployeesService.setMemberAsInactive(empId);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping("/add/serve_impl")
    public ResponseEntity<?>addServingEmployees(@RequestBody ServingEmpDto servingEmpDto){
        servingEmployeesService.addServingEmployees(servingEmpDto);
        return ResponseEntity.ok(new ResponseDto(true,"success"));

    }
    @DeleteMapping("/delete-serve_team")
    public ResponseEntity<?>deleteServingTeam(@RequestParam Integer id){
        servingTeamService.deleteServingTeamById(id);
        return ResponseEntity.ok(new ResponseDto(true,"deleted"));
    }
}
