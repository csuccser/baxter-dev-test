package hu.baxter.web.rest;

import hu.baxter.service.dto.DepartmentDto;
import hu.baxter.service.dto.EmployeeDto;
import hu.baxter.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/employees")
public class EmployeeResource {


    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    public ResponseEntity<List<EmployeeDto>> getEmployees(@RequestParam(required = false) String department) {
        final List<EmployeeDto>  employees;
        if (StringUtils.isBlank(department)) {
            employees = employeeService.getAllEmployees();
        } else {
            employees = employeeService.getEmployeesByDepartment(department);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/groupby/department")
    public ResponseEntity<List<DepartmentDto>> getEmployeesGroupedByDepartment() {
        final List<DepartmentDto> employeesByDepartment = employeeService.getEmployeesGroupedByDepartment();
        return new ResponseEntity<>(employeesByDepartment, HttpStatus.OK);
    }

}
