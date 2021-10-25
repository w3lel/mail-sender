package com.example.workflow;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class TestService implements JavaDelegate, TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        var emailAdress = delegateTask.getVariable("emailAdress").toString();
        var text = delegateTask.getVariable("text").toString();
        final String username = "lakiq00@gmail.com";
        final String password = "Djkjcfnsqgegjr1";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("lakiq00@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(emailAdress)
            );
            message.setSubject("Praktika Lemova");
            message.setText(text);

            Transport.send(message);

            System.out.println("Mail otpravlen");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
    }
}
