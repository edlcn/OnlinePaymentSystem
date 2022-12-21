package com.example.merchant.virtualPos;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class VirtualPos {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bank;

    private Long posId;

    public VirtualPos(String bank, Long posId){
        this.bank = bank;
        this.posId = posId;
    }

}
