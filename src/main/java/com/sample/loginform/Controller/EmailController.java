package com.sample.loginform.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.loginform.Entity.Email;
import com.sample.loginform.Services.EmailService;

import java.time.LocalDateTime;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public String showDashboard(Model model) {
        return "dashboard";
    }

    @PostMapping("/email")
    @ResponseBody
    public Email postDetails(@RequestBody Email email) {
        return emailService.saveDetails(email);
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam("subject") String subject, 
                            @RequestParam("message") String message,
                            @RequestParam("sendTime") String sendTime) {
        LocalDateTime scheduledTime = LocalDateTime.parse(sendTime);
        emailService.scheduleEmail(subject, message, scheduledTime);
        return "redirect:/";
    }
}
