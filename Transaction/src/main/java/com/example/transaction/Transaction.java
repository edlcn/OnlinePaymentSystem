package com.example.transaction;


import com.example.transaction.Serialization.TransactionDTReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;

    private Long receiverId;

    private Double amount;

    private LocalDateTime timeAt;

    private String senderName;



    public Transaction(TransactionDTReq transaction,Long receiverId,String senderName){
        this.amount = transaction.getAmount();
        this.senderId = transaction.getSenderId();
        this.timeAt = LocalDateTime.now();
        this.receiverId = receiverId;
        this.senderName = senderName;
    }
}
