package com.example.demo.service;

import com.example.demo.domain.Employees;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.repository.EmployeesRepository;
import com.example.demo.repository.OffsetBasedPageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

@Transactional
public class EmployeeService {

    @Autowired
    private EmployeesRepository employeesRepository;

    public Employees getEmployee(Long id){
        Optional<Employees> employees = employeesRepository.findById(id);
        return employees.get();
    }

    public List<Employees> getEmployees(){
        return employeesRepository.findAll();
    }

    public List<Employees> getEmployeesMinSalary(Double salary){
        return employeesRepository.findBySalaryGreaterThan(salary);
    }

    public List<Employees> getEmployeesMaxSalary(Double salary){
        return employeesRepository.findBySalaryLessThan(salary);
    }

    public List<Employees> getEmployeesMinMaxSalary(Double min, Double max){
        return employeesRepository.findBySalaryGreaterThanAndSalaryLessThan(min,max);
    }

    public List<Employees> getEmployeesSortByName(){
        return employeesRepository.findByOrderByNameAsc();
    }

    public List<Employees> getUsersSortBySalary(){
        return employeesRepository.findByOrderBySalaryAsc();
    }

    public List<Employees> getAllUsers(int limit, int offset) {
        log.debug("Get all Employees with limit {} and offset {}", limit, offset);
        Pageable pageable = new OffsetBasedPageRequest(limit, offset);
        return employeesRepository.findAll(pageable).getContent();
    }

    public Employees createEmployees(EmployeeDTO employeeDTO){

        Employees employee_existing = employeesRepository.findByName(employeeDTO.getName());

        if(employee_existing == null) {
            return employeesRepository.save(new Employees(employeeDTO.getName(), employeeDTO.getSalary()));
        }
        else {
            employee_existing.setName(employeeDTO.getName());
            employee_existing.setSalary(employeeDTO.getSalary());
            return employeesRepository.save(employee_existing);
        }

    }

    public Employees updateEmployees(EmployeeDTO employeeDTO){
        Employees employee = getEmployee(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        return employeesRepository.save(employee);
    }

    public void deleteEmployees(Long id){
        employeesRepository.deleteById(id);
    }

}
