package com.example.demo.resources;

import com.example.demo.dto.FileUploadDTO;
import com.example.demo.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
public class FileUploadResource {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCsv(@ModelAttribute FileUploadDTO file){

        JSONObject resp = new JSONObject();

        try {

            if(fileUploadService.validate(file)) {
                fileUploadService.parseAndSave(file);
            }
            else {
                resp.put("Success", 0);
                resp.put("Message", "CSV is in wrong format");
                return new ResponseEntity<>(resp,HttpStatus.EXPECTATION_FAILED);
            }
        }catch (Exception e){
            return new ResponseEntity<>("SUCCESS: 0",HttpStatus.OK);
        }
        resp.put("Success", 1);
        resp.put("Message", "Data Imported");
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }
}
