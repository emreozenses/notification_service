package com.tiga.notification_service.service;

import jakarta.mail.MessagingException;

public interface MailService {

    void sendMail(String to, String subject, String body) throws MessagingException;
}
