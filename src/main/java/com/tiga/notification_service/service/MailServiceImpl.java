package com.tiga.notification_service.service;

import com.tiga.notification_service.dto.EmailDetailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService{

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromUsr;


    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void sendMail(String to, String subject, String body) throws MessagingException {
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true,"UTF-8");
        mimeMessageHelper.setFrom(fromUsr);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setText(body,true);
        mimeMessageHelper.setSubject(subject);
        //FileSystemResource file = new FileSystemResource(new File("C:\\Users\\first\\Desktop\\tv.png"));
        //mimeMessageHelper.addAttachment("tv.png",file);
        javaMailSender.send(message);

    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processEmailMessage(EmailDetailDto emailDetailDto) throws MessagingException{

        String to = emailDetailDto.getTo();
        String subject = emailDetailDto.getSubject();
        String body = generateEmailBody(emailDetailDto);
        sendMail(to,subject,body);
    }
    public String generateEmailBody(EmailDetailDto emailDetailDto){
        String templateName = emailDetailDto.getTemplateName();
        String template = loadEmailTemplate(templateName);

        String body = template;
        if(emailDetailDto.getDynamicValue() != null){
            for (Map.Entry<String ,Object> entry: emailDetailDto.getDynamicValue().entrySet()){
                body = body.replace("{{"+entry.getKey()+"}}",entry.getValue().toString());
            }
        }
        return body;
    }

    public String loadEmailTemplate (String templateName){
        ClassPathResource resource = new ClassPathResource("templates/" + templateName + ".html");
        try{
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error loading email template " + templateName, e);
        }

    }




}
