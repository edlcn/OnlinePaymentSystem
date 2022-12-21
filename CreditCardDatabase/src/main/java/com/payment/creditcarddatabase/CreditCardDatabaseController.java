package com.payment.creditcarddatabase;



import com.payment.creditcarddatabase.Serialization.CreditCardDTO;
import com.payment.creditcarddatabase.Serialization.PaymentDTReq;
import com.payment.creditcarddatabase.Serialization.VirtualPostPaymentDTReq;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("classified/ccdb")
@CrossOrigin
public class CreditCardDatabaseController {

    private final CreditCardDatabaseService creditCardDatabaseService;

    public CreditCardDatabaseController(CreditCardDatabaseService creditCardDatabaseService) {
        this.creditCardDatabaseService = creditCardDatabaseService;
    }


    @GetMapping("{id}")
    public CreditCardDatabase getCard(@PathVariable Long id){
        return creditCardDatabaseService.getCard(id);
    }


    @PostMapping
    public String addCard(@RequestBody CreditCardDatabase ccdb){
        creditCardDatabaseService.addCard(ccdb);
        return "Success.";
    }


    @PostMapping(value = "/validate",consumes = {"*/*"})
    public Integer validateCard(@RequestBody CreditCardDTO card){
        return creditCardDatabaseService.validateCard(card);
    }


    @PostMapping("/payment")
    public Integer paymentAuth(@RequestBody PaymentDTReq payment){
        return creditCardDatabaseService.paymentAuth(payment);
    }

    @PostMapping("/vpPayment")
    public Integer virtualPosPaymentAuth(@RequestBody VirtualPostPaymentDTReq payment){
        return creditCardDatabaseService.virtualPosPaymentAuth(payment);
    }


}
