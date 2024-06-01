package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.dto.team.DecorEmpDto;
import com.catering_app.Catering_app.dto.team.TeamDto;
import com.catering_app.Catering_app.model.teams.DecorationEmployees;
import com.catering_app.Catering_app.model.teams.DecorationTeam;
import com.catering_app.Catering_app.service.teamServices.decorationEmployees.DecorationEmployeeService;
import com.catering_app.Catering_app.service.teamServices.decorationTeam.DecorationTeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DecorationTeamController {


    private final DecorationTeamService decorationTeamService;
    private final DecorationEmployeeService decorationEmployeeService;

    public DecorationTeamController(DecorationTeamService decorationTeamService, DecorationEmployeeService decorationEmployeeService) {
        this.decorationTeamService = decorationTeamService;
        this.decorationEmployeeService = decorationEmployeeService;
    }


    @PostMapping("/add/decor_team")
    public ResponseEntity<DecorationTeam> addTeam(@RequestBody TeamDto teamDto){
        return ResponseEntity.ok(decorationTeamService.addDecorationTeam(teamDto));
    }

    @GetMapping("/decor_teams")
    public ResponseEntity<List<DecorationTeam>> getAllServingTeams(){
        return ResponseEntity.ok(decorationTeamService.getAllDecorationTeam());
    }
    @GetMapping("/decor_emp")
    public ResponseEntity<List<DecorationEmployees>> getAllServingEmployees(){
        return ResponseEntity.ok(decorationEmployeeService.getAllDecorationEmployees());
    }


    @PostMapping("/add/decor_emp")
    public ResponseEntity<?>addServingEmployees(@RequestBody DecorEmpDto decorEmpDto){
        decorationEmployeeService.addDecorationEmployees(decorEmpDto);
        return ResponseEntity.ok(new ResponseDto(true,"success"));

    }
    @DeleteMapping("/delete-dec_team")
    public ResponseEntity<?>deleteDecorationTeam(@RequestParam Integer id){
        decorationTeamService.deleteDecorationTeamById(id);
        return ResponseEntity.ok(new ResponseDto(true,"deleted"));
    }

    @GetMapping("/get/dec-emp")
    public ResponseEntity<List<DecorationEmployees>> getDecorationEmployeesBYTeam(@RequestParam Integer id){
        return ResponseEntity.ok(decorationEmployeeService.getDecorationEmpByDecorationTeamId(id));
    }

}
