package com.example.payment.Serialization;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantPaymentDTReq {

    private Long merchantId;

    private String name;

    private Double amount;
}
