package com.tiga.notification_service.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiga.notification_service.entity.Patient;
import com.tiga.notification_service.repository.PatientRepository;
import com.tiga.notification_service.service.MailService;
import com.tiga.notification_service.service.PatientService;
import com.tiga.notification_service.service.RabbitMQProducer;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;
    private PatientService patientService;
    private RabbitMQProducer rabbitMQProducer;
    private final PatientRepository patientRepository;
    private static final String getUrl = "http://localhost:9000/tiga/patient/";
    private static final String postUrl = "http://localhost:9001/tigaNotification/test/";
    private RestTemplate restTemplate;
    private List<Patient> responseBody = new ArrayList<>();


    @Autowired
    public MailController(MailService mailService, PatientService patientService, RabbitMQProducer rabbitMQProducer, PatientRepository patientRepository, RestTemplate restTemplate) {
        this.mailService = mailService;
        this.patientService = patientService;
        this.rabbitMQProducer = rabbitMQProducer;
        this.patientRepository = patientRepository;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/send")
    public ResponseEntity<List<Patient>> sendMail() throws MessagingException {
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

        List<Patient> patientList = new ArrayList<>();
        patientList.addAll(patientService.findMaleAndNotificationEMAIL());

        patientList.forEach(patient -> {
            rabbitMQProducer.sendEmailMessage(patient,patient.getId(),"verification");
                });

        return ResponseEntity.ok(patientList);
    }


}
