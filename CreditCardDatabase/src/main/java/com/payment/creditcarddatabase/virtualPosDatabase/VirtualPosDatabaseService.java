package com.payment.creditcarddatabase.virtualPosDatabase;


import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class VirtualPosDatabaseService {

    private final VirtualPosDatabaseDAO virtualPosDatabaseDAO;

    public VirtualPosDatabaseService(VirtualPosDatabaseDAO virtualPosDatabaseDAO) {
        this.virtualPosDatabaseDAO = virtualPosDatabaseDAO;
    }

    @PostConstruct
    public void addPossesToDb(){
        VirtualPosDatabase vp = new VirtualPosDatabase("TR204000566588456327453759");
        virtualPosDatabaseDAO.save(vp);

    }
}
