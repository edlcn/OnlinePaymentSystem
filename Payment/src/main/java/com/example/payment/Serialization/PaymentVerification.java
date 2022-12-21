package com.example.payment.Serialization;


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
