package com.catering_app.Catering_app.service.teamServices.decorationEmployees;

import com.catering_app.Catering_app.dto.team.DecorEmpDto;
import com.catering_app.Catering_app.model.Employee;
import com.catering_app.Catering_app.model.teams.DecorationEmployees;
import com.catering_app.Catering_app.model.teams.DecorationTeam;
import com.catering_app.Catering_app.repository.DecorationEmployeeRepository;
import com.catering_app.Catering_app.service.employeeService.EmployeeService;
import com.catering_app.Catering_app.service.teamServices.decorationTeam.DecorationTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DecorationEmployeeServiceImpl implements DecorationEmployeeService{


    private final DecorationEmployeeRepository decorationEmployeeRepository;
    private final DecorationTeamService decorationTeamService;
    private final EmployeeService employeeService;


    @Override
    public void addDecorationEmployees(DecorEmpDto decorEmpDto) {
        Optional<Employee> optionalEmployee = employeeService.getEmployeeById(decorEmpDto.getEmp());
        if (optionalEmployee.isEmpty()) {
            throw new IllegalArgumentException("Employee not found");
        }
        Optional<DecorationTeam> optionalDecorationTeam = decorationTeamService.getDecorationTeamById(decorEmpDto.getDecorationTeamId());
        DecorationTeam decorationTeam = optionalDecorationTeam.orElseThrow(() -> new IllegalArgumentException("Team not found"));

        DecorationEmployees decorationEmployees = new DecorationEmployees();

            decorationEmployees.setEmp(optionalEmployee.get());
            decorationEmployees.setDecorationTeam(decorationTeam);
            int count = decorationTeam.getDecorationTeamMembers().size();
            decorationTeam.setCount(count);
            decorationEmployeeRepository.save(decorationEmployees);

    }

    @Override
    public List<DecorationEmployees> getAllDecorationEmployees() {
        return decorationEmployeeRepository.findAll();
    }

    @Override
    public List<DecorationEmployees> getDecorationEmpByDecorationTeamId(Integer id) {
        return decorationEmployeeRepository.getDecorationEmployeeByDecorationTeamId(id);
    }
}
