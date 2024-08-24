package com.tiga.notification_service.controller;

import com.tiga.notification_service.service.RabbitMQProducer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientNotificationController {
    private RabbitMQProducer rabbitMQProducer;
}
