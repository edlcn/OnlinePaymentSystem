package com.example.payment.Serialization;

import com.example.payment.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantPaymentFinalization {

    private CreditCard creditCard;

    private Double amount;

    private Long virtualPosId;
}
