package com.sample.loginform.Services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.sample.loginform.Entity.Email;
import com.sample.loginform.Repository.EmailRepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepo emailRepo;

    public Email saveDetails(Email email) {
        return emailRepo.save(email);
    }

    public void sendEmails(String subject, String message) {
        List<Email> emailAddresses = emailRepo.findAll();

        for (Email emailAddress : emailAddresses) {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setTo(emailAddress.getEmail());
                helper.setSubject(subject);
                helper.setText("<html><body>" + message + "</body></html>", true);
                mailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public void scheduleEmail(String subject, String message, LocalDateTime sendTime) {
        Date date = Date.from(sendTime.atZone(ZoneId.systemDefault()).toInstant());
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendEmails(subject, message);
            }
        }, date);
    }
}
