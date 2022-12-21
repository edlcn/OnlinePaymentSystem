package com.example.payment.Serialization;


import com.example.payment.merchantPayment.MerchantPayment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantPaymentDTO {
    private Long id;

    private String name;

    private LocalDateTime date;

    private Double amount;



}
