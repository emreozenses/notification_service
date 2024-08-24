package com.tiga.notification_service.service;

import com.tiga.notification_service.repository.MailRepository;
import jakarta.mail.MessagingException;

public interface MailService {

    String sendMail();

    String sendMultiMediaMail() throws MessagingException;



}
