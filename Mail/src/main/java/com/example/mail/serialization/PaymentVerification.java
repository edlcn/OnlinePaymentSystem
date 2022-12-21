package com.example.mail.serialization;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentVerification {

    private String to;
    private String merchantName;
    private Double amount;
}
