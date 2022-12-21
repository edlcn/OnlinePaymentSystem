package com.example.payment.merchantPayment;


import com.example.payment.Serialization.MerchantPaymentDTO;
import com.example.payment.Serialization.MerchantPaymentDTReq;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class MerchantPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Long uid;

    private Double amount;

    private Long merchantId;

    private String name;

    private LocalDateTime date;

    private boolean isCompleted;

    public MerchantPayment(MerchantPaymentDTReq payment){
        this.uid = null;
        this.token = UUID.randomUUID().toString();
        this.amount = payment.getAmount();
        this.merchantId = payment.getMerchantId();
        this.isCompleted = false;
        this.date  = LocalDateTime.now();
        this.name = payment.getName();
    }


    public static MerchantPaymentDTO convertToDTO(MerchantPayment merchantPayment){
        return new MerchantPaymentDTO(merchantPayment.getId(), merchantPayment.getName(), merchantPayment.getDate(), merchantPayment.getAmount());
    }

}
