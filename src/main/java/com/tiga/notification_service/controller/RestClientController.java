package com.tiga.notification_service.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiga.notification_service.entity.Patient;
import com.tiga.notification_service.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/test")
public class RestClientController {

    private PatientService patientService;

    private static final String getUrl = "http://localhost:9000/tiga/patient/";
    private static final String postUrl = "http://localhost:9001/tigaNotification/test/";
    private RestTemplate restTemplate;
    private List<Patient> responseBody = new ArrayList<>();
    @Autowired
    public RestClientController(PatientService patientService, RestTemplate restTemplate) {
        this.patientService = patientService;
        this.restTemplate = restTemplate;
    }
    @PostMapping("/")
    public ResponseEntity<List<Patient>> getPatients(){
        ResponseEntity<List> result = restTemplate.getForEntity(getUrl, List.class);
        responseBody = result.getBody();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ObjectMapper mapper = new ObjectMapper();
        List<Patient> pojos = mapper.convertValue(responseBody, new TypeReference<List<Patient>>() { });

        for (Patient pojo : pojos) {
        patientService.save(pojo);
        }
        patientService.deleteDuplicatePatient();
        return ResponseEntity.ok(pojos);
    }




}
