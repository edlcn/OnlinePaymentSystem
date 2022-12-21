package com.example.payment.Serialization;

import com.example.payment.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTReq {

    private CreditCard creditCard;
    private Double price;

}
