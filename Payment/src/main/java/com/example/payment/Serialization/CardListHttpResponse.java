package com.example.payment.Serialization;

import com.example.payment.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardListHttpResponse {

    private String status;

    private List<CreditCard> returnObject;

}
