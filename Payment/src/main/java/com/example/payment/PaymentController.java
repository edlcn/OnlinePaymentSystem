package com.example.payment;


import com.example.payment.Serialization.GeneralHttpResponse;
import com.example.payment.Serialization.PaymentDTReq;
import com.example.payment.Serialization.SessionIdDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @CrossOrigin
    @PostMapping
    public GeneralHttpResponse<String> addPayment(@RequestBody PaymentDTReq payment,
                                                  @RequestHeader("id") Long id,
                                                  @RequestHeader("sessionId") String sessionId){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        SessionIdDTO IncSessionId = new SessionIdDTO(id,sessionId);
        try{
            paymentService.addPayment(payment,IncSessionId);
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }

        return response;
    }

    @CrossOrigin
    @GetMapping("cre")
    public GeneralHttpResponse<List<Payment>> getCreditCardRecord(@RequestParam(required = false) String number,
                                             @RequestHeader("id") Long id,
                                             @RequestHeader("sessionId") String sessionId){

        GeneralHttpResponse<List<Payment>> response = new GeneralHttpResponse<>("200",null);
        SessionIdDTO IncSessionId = new SessionIdDTO(id,sessionId);
        try {
            response.setReturnObject(paymentService.getCreditCardRecord(number,IncSessionId));
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;
    }

    @CrossOrigin
    @GetMapping
    public GeneralHttpResponse<List<Payment>> getUserRecord(@RequestParam(required = false) Long uid,
                                       @RequestHeader("id") Long id,
                                       @RequestHeader("sessionId") String sessionId){
        GeneralHttpResponse<List<Payment>> response = new GeneralHttpResponse<>("200",null);
        SessionIdDTO IncSessionId = new SessionIdDTO(id,sessionId);

        try{
            response.setReturnObject(paymentService.getUserRecord(uid,IncSessionId));
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;
    }

}
