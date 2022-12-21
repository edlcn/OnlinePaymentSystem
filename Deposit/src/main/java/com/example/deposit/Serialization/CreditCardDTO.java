package com.example.deposit.Serialization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreditCardDTO {

    private String name;
    private String number;
    private String date;
    private Integer cvv;


}
