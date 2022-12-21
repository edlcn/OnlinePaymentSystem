package com.example.payment;


import com.example.payment.Serialization.PaymentDTReq;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private LocalDateTime timeAt;
    private String number; // reference to creditcard db

    public Payment(PaymentDTReq payment){
        this.price = payment.getPrice();
        this.number = payment.getCreditCard().getNumber();
        this.timeAt = LocalDateTime.now();
    }
}
