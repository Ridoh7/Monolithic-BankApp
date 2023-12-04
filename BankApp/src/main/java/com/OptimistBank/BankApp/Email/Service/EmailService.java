package com.OptimistBank.BankApp.Email.Service;

import com.OptimistBank.BankApp.Email.dto.EmailDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailService {

    String sendSimpleEmail(EmailDetails emailDetails);
    String sendMailWithAttachment(EmailDetails details);
}
