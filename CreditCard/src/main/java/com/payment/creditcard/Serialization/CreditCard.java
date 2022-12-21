package com.payment.creditcard.Serialization;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreditCard implements Serializable {

    // Bu data için NOSQL kullanmak daha mantıklı değil mi ?
    @Id
    private String number;

    private String date;

    private String name;

    private Integer cvv;

    @JsonIgnore
    private Long userId;

    public CreditCard(CreditCardDTReq card){
        CreditCard copy = card.getCreditCard();
        this.userId = card.getUserId();
        this.cvv = copy.getCvv();
        this.date = copy.getDate();
        this.name = copy.getName();
        this.number = copy.getNumber();

    }

}
