package com.example.merchant.serialization;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantVposReq {
    private Integer status;

    private Long vpos;
}
