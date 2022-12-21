package com.payment.creditcard;


import com.payment.creditcard.Serialization.CardListHttpResponse;
import com.payment.creditcard.Serialization.CreditCardDTReq;
import com.payment.creditcard.Serialization.GeneralHttpResponse;
import com.payment.creditcard.Serialization.SessionIdDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cards")
public class CreditCardController {

    private final CreditCardService creditCardService;


    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }


    @CrossOrigin
    @GetMapping
    public CardListHttpResponse getCards(@RequestParam Long uid){
        //Generic type'tan sabit type deserializationda problem oldu yeni bir class oluşturmak zorunda kaldım. -> CardListHttpResponse

        CardListHttpResponse response = new CardListHttpResponse("200",null);
        //SessionIdDTO IncSessionId = new SessionIdDTO(id,sessionId);
        try {
            response.setReturnObject(creditCardService.getCards(uid));
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;



    }

    @CrossOrigin
    @PostMapping
    public GeneralHttpResponse<String> addCard(@RequestBody CreditCardDTReq creditCard,
                                               @RequestHeader("id") Long id,
                                               @RequestHeader("sessionId") String sessionId){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        SessionIdDTO IncSessionId = new SessionIdDTO(id,sessionId);
        try{
            creditCardService.addCard(creditCard,IncSessionId);
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }

        return response;
    }

    @CrossOrigin
    @DeleteMapping("/delete")
    public GeneralHttpResponse<String> deleteCard(@RequestBody CreditCardDTReq creditCard,
                                                  @RequestHeader("id") Long id,
                                                  @RequestHeader("sessionId") String sessionId){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        SessionIdDTO IncSessionId = new SessionIdDTO(id,sessionId);
        try {
            creditCardService.deleteCard(creditCard,IncSessionId);
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;
    }
}
