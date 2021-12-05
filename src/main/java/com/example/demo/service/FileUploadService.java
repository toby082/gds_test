package com.example.demo.service;

import com.example.demo.domain.Employees;
import com.example.demo.dto.FileUploadDTO;
import com.example.demo.repository.EmployeesRepository;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;

@Service
@Slf4j
public class FileUploadService {

    @Autowired
    private EmployeesRepository employeesRepository;

    public void parseAndSave(FileUploadDTO file){
        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader( file.getFile().getInputStream()) );
            while( (line = bufferedReader.readLine()) != null ) {
                String[] emps = line.split(",");

                Employees employee_existing = employeesRepository.findByName(emps[0]);
                if(employee_existing == null) {
                    employeesRepository.save(new Employees(emps[0],Double.parseDouble(emps[1])));
                }
                else {
                    employee_existing.setName(emps[0]);
                    employee_existing.setSalary(Double.parseDouble(emps[1]));
                    employeesRepository.save(employee_existing);
                }
            }
        }catch (Exception e){
            log.error("error occurred while storing data to DB");
        }
    }

    // Vlidate CSV data santity
    public Boolean validate(FileUploadDTO file){
        log.info("validate csv file before saving data into DB");
        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader( file.getFile().getInputStream()) );
            while( (line = bufferedReader.readLine()) != null ) {
                String[] emps = line.split(",");

                boolean isNumeric = NumberUtils.isCreatable(emps[1]);
                if ( emps.length != 2 || !isNumeric || Double.valueOf(emps[1]) < 0.0 ) {
                    return false;
                }
            }
        }catch (Exception e){
            log.error("error occurred while validating csv");
            return false;
        }
        return true;
    }

}
