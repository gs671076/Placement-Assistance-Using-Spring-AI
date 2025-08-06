/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.placement.PA.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author gs671
 */
@Service
@Slf4j
public class EmailService {
    @Autowired
   private JavaMailSender javaMailSender; 
    public void sendEmail(String to, String subject, String body)
    {try
    {
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);
        this.javaMailSender.send(mail);
    }
    catch(MailException e)
    {
    log.error("Exception occurred while sending mail "+e);
    }
    }
}
