package com.payment.creditcarddatabase;


import com.payment.creditcarddatabase.Serialization.CreditCardDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bank;

    @Column(unique = true)
    private String number;

    private String name;

    private String date;

    private Integer cvv;

    private String mail;

    private String phone;

    private String identity;

    private Double balance;

    @Column(unique = true)
    private String iban;




    public boolean equals(CreditCardDTO inc) {
        return this.name.equals( inc.getName()) && this.date.equals(inc.getDate()) && this.cvv.equals(inc.getCvv()) && this.number.equals(inc.getNumber());

    }
}
