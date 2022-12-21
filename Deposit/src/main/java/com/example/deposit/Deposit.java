package com.example.deposit;


import com.example.deposit.Serialization.DepositDTReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long depositorId;

    private Double amount;

    private LocalDateTime timeAt;

    private String card;

    public Deposit(DepositDTReq deposit){
        this.depositorId = deposit.getDepositorId();
        this.amount = deposit.getAmount();
        this.card = deposit.getCard().getNumber();
        this.timeAt = LocalDateTime.now();
    }
}
