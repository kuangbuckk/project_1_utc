package com.project.ticketBooking.controllers;

import com.project.ticketBooking.models.EmailDetail;
import com.project.ticketBooking.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("${api.prefix}/emails")
public class EmailController {
    private EmailService emailService;

    @PostMapping("/sendEmail")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String sendEmail(
            @RequestBody EmailDetail details
    ){
        return emailService.sendMail(details);
    }
}
