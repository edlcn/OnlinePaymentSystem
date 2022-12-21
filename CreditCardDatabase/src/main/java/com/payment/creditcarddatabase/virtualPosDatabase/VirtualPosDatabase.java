package com.payment.creditcarddatabase.virtualPosDatabase;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class VirtualPosDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String relatedAccountIban;

    public VirtualPosDatabase(String relatedAccountIban){
        this.relatedAccountIban = relatedAccountIban;
    }
}
