package com.example.users.confirmationToken;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("validate")
public class ConfirmationTokenController {
    private final ConfirmationTokenService confirmationTokenService;


    public ConfirmationTokenController(ConfirmationTokenService confirmationTokenService) {
        this.confirmationTokenService = confirmationTokenService;

    }

    @CrossOrigin
    @GetMapping
    public String mailConfirmation(@RequestParam String confirmationToken){
        try {
            confirmationTokenService.mailConfirmation(confirmationToken);
            return "You have successfully confirmed your account.";
        }
        catch (Exception e){
            return "Confirmation failed!";
        }
    }
}
