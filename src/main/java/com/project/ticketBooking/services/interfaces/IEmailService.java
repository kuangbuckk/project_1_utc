package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.models.EmailDetail;

public interface IEmailService {
    String sendMail(EmailDetail details);
//    String sendMailWithAttachment(EmailDetail details);
}
