package com.example.merchant;


import com.example.merchant.serialization.GeneralHttpResponse;
import com.example.merchant.serialization.MerchantDTReq;

import com.example.merchant.serialization.MerchantVposReq;
import com.example.merchant.serialization.VposId;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/merchant")
@CrossOrigin
public class MerchantController {


    private final MerchantService merchantService;


    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }


    @PostMapping
    public GeneralHttpResponse<String> merchantApplication(@RequestBody MerchantDTReq merchant){

        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try {
            merchantService.merchantApplication(merchant);
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }

        return response;
    }

    @GetMapping("/{uid}/vpos")
    public VposId getVposId(@PathVariable Long uid){
        return new VposId(merchantService.getVposId(uid));
    }

}
