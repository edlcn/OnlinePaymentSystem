package com.example.merchant;



import com.example.merchant.serialization.MerchantDTReq;
import com.example.merchant.virtualPos.VirtualPos;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class Merchant {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String mail;

    @OneToOne
    private VirtualPos virtualPos;

    public Merchant(MerchantDTReq merchant){
        this.name = merchant.getName();
        this.mail = merchant.getMail();
        this.virtualPos = merchant.getVirtualPos();
    }

    public Merchant(String name, String mail, VirtualPos vp){
        this.name = name;
        this.mail = mail;
        this.virtualPos = vp;
    }



}
