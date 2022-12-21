package com.example.merchant.virtualPos;


import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class VirtualPosService {

    private final VirtualPosDAO virtualPosDAO;

    public VirtualPosService(VirtualPosDAO virtualPosDAO) {
        this.virtualPosDAO = virtualPosDAO;
    }

    @PostConstruct
    public void initializeVpos(){
        VirtualPos vp = new VirtualPos("Is Bankasi", 1L);
        virtualPosDAO.save(vp);
    }
}
