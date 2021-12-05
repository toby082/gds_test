package com.example.demo.repository;

import java.util.List;

import com.example.demo.domain.Employees;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees,Long> {

    public Employees findByName(String name);

    public List<Employees> findBySalaryGreaterThan(Double salary);
    
    public List<Employees> findBySalaryLessThan(Double salary);

    public List<Employees> findBySalaryGreaterThanAndSalaryLessThan(Double min, Double max);

    public List<Employees> findByOrderByNameAsc();

    public List<Employees> findByOrderBySalaryAsc();

}
