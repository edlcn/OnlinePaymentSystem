package com.example.mail;


import com.example.mail.serialization.ConfirmationMail;
import com.example.mail.serialization.PaymentVerification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {


    private final JavaMailSender mailSender;

    public MailService( JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public Integer sendConfirmationMail(ConfirmationMail confirmationMail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("emrhn2001@gmail.com");
        mailMessage.setTo(confirmationMail.getMail());
        mailMessage.setSubject("MKB Express Registration Confirmation");
        mailMessage.setText("Activate your account by clicking this link:  " + confirmationMail.getLink());
        try {
            mailSender.send(mailMessage);
            return 1;
        }
        catch (Exception e){
            return 0;
        }

    }

    public Integer sendPaymentVerification(PaymentVerification paymentVerification) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("emrhn2001@gmail.com");
        mailMessage.setTo(paymentVerification.getTo());
        mailMessage.setSubject("MKB Express Payment Verification");
        mailMessage.setText(String.format("We have received your payment: Details: \n From: %s\n Amount: %s",paymentVerification.getMerchantName(),paymentVerification.getAmount()));

        log.info("payment");
        try {
            mailSender.send(mailMessage);
            return 1;
        }
        catch (Exception e){
            return 0;
        }
    }
}
