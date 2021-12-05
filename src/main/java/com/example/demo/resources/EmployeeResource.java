package com.example.demo.resources;

import com.example.demo.domain.Employees;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@Slf4j
public class EmployeeResource {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getEmps(@PathVariable Long id){
        Employees employees = employeeService.getEmployee(id);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getEmps(){
        List<Employees> employees = employeeService.getEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/usres")
    public ResponseEntity<?> controllerMethod(@RequestParam Map<String, String> customQuery) {

        if(customQuery.get("min") != null && customQuery.get("max") == null) {
            List<Employees> employees = employeeService.getEmployeesMinSalary(Double.parseDouble(customQuery.get("min")));
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }

        if( customQuery.get("max") != null && customQuery.get("min") == null) {
            List<Employees> employees = employeeService.getEmployeesMaxSalary(Double.parseDouble(customQuery.get("max")));
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }

        if( customQuery.get("min") != null && customQuery.get("max") != null) {
            List<Employees> employees = employeeService.getEmployeesMinMaxSalary(Double.parseDouble(customQuery.get("min")), Double.parseDouble(customQuery.get("max")));
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }

        if( customQuery.get("offset") != null && customQuery.get("limit") != null && customQuery.get("min") == null && customQuery.get("max") == null) {
            List<Employees> employees = employeeService.getAllUsers( Integer.parseInt(customQuery.get("limit")), Integer.parseInt(customQuery.get("offset")));
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }

        if ( customQuery.get("sort") == "NAME"){
            List<Employees> employees = employeeService.getEmployeesSortByName();
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }

        if ( customQuery.get("sort") == "SALARY"){
            List<Employees> employees = employeeService.getUsersSortBySalary();
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }

        List<Employees> employees = employeeService.getEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createEmps(@RequestBody EmployeeDTO employeeDTO){
        Employees employees = employeeService.createEmployees(employeeDTO);
        return new ResponseEntity<>(employees, HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<?> updateEmps(@RequestBody EmployeeDTO employeeDTO){
        Employees employees = employeeService.updateEmployees(employeeDTO);
        return new ResponseEntity<>(employees, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteEmps(@PathVariable Long id){
        employeeService.deleteEmployees(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
