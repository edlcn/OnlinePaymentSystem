package com.payment.creditcarddatabase.Serialization;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VirtualPostPaymentDTReq {

    private Long virtualPosId;

    private Double amount;

    private CreditCardDTO creditCard;

}
