package com.project.ticketBooking.controllers;

import com.project.ticketBooking.models.EmailDetail;
import com.project.ticketBooking.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("${api.prefix}/email")
public class EmailController {
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public String sendEmail(
            @RequestBody EmailDetail details
    ){
        return emailService.sendMail(details);
    }
}
