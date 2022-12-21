package com.example.mail;

import com.example.mail.serialization.ConfirmationMail;
import com.example.mail.serialization.PaymentVerification;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("classified/mail")
@CrossOrigin
public class MailController {

    private final MailService mailService;


    public MailController( MailService mailService) {
        this.mailService = mailService;

    }



    @PostMapping("registration")
    public Integer sendConfirmationMail(@RequestBody ConfirmationMail confirmationMail){
         return mailService.sendConfirmationMail(confirmationMail);
    }

    @PostMapping("payment")
    public Integer sendPaymentVerification(@RequestBody PaymentVerification paymentVerification){
        return mailService.sendPaymentVerification(paymentVerification);
    }

}
