package com.example.merchant.serialization;

import com.example.merchant.virtualPos.VirtualPos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDTReq {

    private String name;

    private String mail;

    private VirtualPos virtualPos;
}
