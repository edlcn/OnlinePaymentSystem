package com.example.payment.merchantPayment;


import com.example.payment.CreditCard;
import com.example.payment.Serialization.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/merchantPayment")
@CrossOrigin
public class MerchantPaymentController {


    private final MerchantPaymentService merchantPaymentService;

    public MerchantPaymentController(MerchantPaymentService merchantPaymentService) {
        this.merchantPaymentService = merchantPaymentService;
    }

    //normalde karşı backendden gelmesi lazım fakat basit olması için direkt fronttan gelecek.
    @PostMapping
    public GeneralHttpResponse<String> merchantPaymentRequest(@RequestBody MerchantPaymentDTReq merchantPayment){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(merchantPaymentService.merchantPaymentRequest(merchantPayment));
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;
    }

    @GetMapping("/{token}")
    public GeneralHttpResponse<MerchantPaymentDTRes> merchantPaymentPage(@PathVariable String token){
        GeneralHttpResponse<MerchantPaymentDTRes> response = new GeneralHttpResponse<>("200",null);
        try {
            response.setReturnObject(merchantPaymentService.merchantPaymentPage(token));
        }
        catch (Exception e){
            response.setStatus("400:"+ e.getMessage());
        }
        return response;
    }




    //todo: ödemeyi ala tıklandığında buraya gelecek.
    @PostMapping("/complete")
    public GeneralHttpResponse<String> completePayment(@RequestBody CreditCard creditCard, @RequestParam String token){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try{
            merchantPaymentService.completePayment(creditCard,token);
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }

        return response;
    }

    @PostMapping("setUser")
    public Integer setUser(@RequestBody SetUserDTO userDTO){
        return merchantPaymentService.setUser(userDTO);
    }


    @GetMapping
    public GeneralHttpResponse<List<MerchantPaymentDTO>> getUserMerchantPayments(@RequestParam(required = false) Long uid){
        GeneralHttpResponse<List<MerchantPaymentDTO>> response = new GeneralHttpResponse<>("200",null);

        try {
            response.setReturnObject(merchantPaymentService.getUserMerchantPayments(uid));
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;
    }



}
