package com.tiga.notification_service.service;

import com.tiga.notification_service.repository.MailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailServiceImpl implements MailService{

    private final JavaMailSender javaMailSender;


    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public String sendMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("noreply@eozmailservice.com");
        simpleMailMessage.setTo("emreozenses@gmail.com");
        simpleMailMessage.setText("Merhaba.Bu deneme mesaji size gonderildi");
        simpleMailMessage.setSubject("Onemli Deneme");
        javaMailSender.send(simpleMailMessage);
        return "Gonderildi";
    }

    @Override
    public String sendMultiMediaMail() throws MessagingException {
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true);
        mimeMessageHelper.setFrom("noreply@eozmailservice.com");
        mimeMessageHelper.setTo("emreozenses@gmail.com");
        mimeMessageHelper.setText("Merhaba.Bu deneme mesaji size gonderildi");
        mimeMessageHelper.setSubject("Onemli Deneme");
        FileSystemResource file = new FileSystemResource(new File("C:\\Users\\first\\Desktop\\tv.png"));
        mimeMessageHelper.addAttachment("tv.png",file);
        javaMailSender.send(message);
        return "Gonderildi";
    }
}
