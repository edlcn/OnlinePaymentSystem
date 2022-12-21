package com.example.merchant;


import com.example.merchant.serialization.MerchantDTReq;
import com.example.merchant.virtualPos.VirtualPos;
import com.example.merchant.virtualPos.VirtualPosDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class MerchantService {

    private final MerchantDAO merchantDAO;
    private final VirtualPosDAO virtualPosDAO;


    public MerchantService(MerchantDAO merchantDAO, VirtualPosDAO virtualPosDAO) {
        this.merchantDAO = merchantDAO;
        this.virtualPosDAO = virtualPosDAO;
    }

    public void merchantApplication(MerchantDTReq merchant) {

        if (merchantDAO.existsByMail(merchant.getMail())) throw new IllegalStateException("MAIL ALREADY IN USE!");
        //todo: vp'yi al bankada sorgula yoksa error dön.

        Merchant toBeAdded = new Merchant(merchant);
        merchantDAO.save(toBeAdded);


    }

    public Long getVposId(Long uid) {
        Merchant merchant = merchantDAO.findMerchantById(uid);
        if (merchant == null) return null;
        return merchant.getVirtualPos().getPosId();

    }



    @PostConstruct
    public void addMerchant(){
        VirtualPos vp = virtualPosDAO.findVirtualPosById(1L);
        Merchant merchant = new Merchant("YABE LIMITED","YABE@gmail.com",vp);
        merchantDAO.save(merchant);

    }
}
