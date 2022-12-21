package com.example.transaction.Serialization;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTReq {

    private Long senderId;

    private String receiverMail;

    private Double amount;
}
