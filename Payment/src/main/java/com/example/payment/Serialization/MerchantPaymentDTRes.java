package com.example.payment.Serialization;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantPaymentDTRes {

    private Double amount;

    private String merchantName;


    private String username;


}
