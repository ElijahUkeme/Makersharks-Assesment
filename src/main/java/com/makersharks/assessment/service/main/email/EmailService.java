package com.makersharks.assessment.service.main.email;
import com.makersharks.assessment.model.email.EmailDetails;
import com.makersharks.assessment.security.dto.EmailDto;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    @Async
    void sendEmailAlert(EmailDetails emailDetails);

    @Async
    void sendConfirmationEmail(EmailDto emailDto) throws MessagingException;
}
