package com.example.deposit.Serialization;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepositDTReq {

    private Long depositorId;

    private Double amount;

    private CreditCardDTO card;
}
