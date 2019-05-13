package com.adam.kiss.jbugger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendPasswordToUser(String email, String username, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your JBugger registration.");
        message.setText("Hi " + username + "!\nYour temporary password is: " + password + ".");
        javaMailSender.send(message);
    }
}
