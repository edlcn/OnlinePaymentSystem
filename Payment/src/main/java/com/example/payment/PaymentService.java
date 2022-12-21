package com.example.payment;

import com.example.payment.Serialization.CardListHttpResponse;
import com.example.payment.Serialization.PaymentDTReq;
import com.example.payment.Serialization.SessionIdDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentDAO paymentDAO;
    private final RestTemplate restTemplate;


    public PaymentService(PaymentDAO paymentDAO, RestTemplate restTemplate) {
        this.paymentDAO = paymentDAO;
        this.restTemplate = restTemplate;
    }

    public void addPayment(PaymentDTReq payment, SessionIdDTO sessionId) {
        Integer tokenValidityCheck = restTemplate.postForObject("http://session:8080/api/v1/session/validate",sessionId,Integer.class);
        if (tokenValidityCheck == null || tokenValidityCheck == 0) throw new IllegalStateException("INVALID TOKEN!");
        if (tokenValidityCheck == -1) throw new IllegalStateException("TOKEN EXPIRED!");

        Integer paymentAuth = restTemplate.postForObject("http://credit-card-db:8080/classified/ccdb/payment",payment,Integer.class);
        if (paymentAuth == null || paymentAuth == 0) throw new IllegalStateException("PAYMENT FAILED!");

        Payment toBeAdded = new Payment(payment);
        paymentDAO.save(toBeAdded);
    }

    public List<Payment> getCreditCardRecord(String number,SessionIdDTO sessionId) {
        Integer tokenValidityCheck = restTemplate.postForObject("http://session/api/v1/session/validate",sessionId,Integer.class);
        if (tokenValidityCheck == null || tokenValidityCheck == 0) throw new IllegalStateException("INVALID TOKEN!");
        if (tokenValidityCheck == -1) throw new IllegalStateException("TOKEN EXPIRED!");

        return paymentDAO.findPaymentsByNumber(number);
    }

    // tokeni burda değil diğer tarafta checkle.
    public List<Payment> getUserRecord(Long uid,SessionIdDTO sessionId) {
        //todo:header olarak token eklenecek.
        Integer tokenValidityCheck = restTemplate.postForObject("http://session:8080/api/v1/session/validate",sessionId,Integer.class);
        if (tokenValidityCheck == null || tokenValidityCheck == 0) throw new IllegalStateException("INVALID TOKEN!");
        if (tokenValidityCheck == -1) throw new IllegalStateException("TOKEN EXPIRED!");

        CardListHttpResponse userCardList = restTemplate.getForObject("http://credit-card:8080/api/v1/cards?uid="+uid,CardListHttpResponse.class);
        if(userCardList == null || userCardList.getReturnObject() == null) throw new IllegalStateException("SOMETHING WENT WRONG!");

        List<Payment> userPaymentRecord = new ArrayList<>();
        for (CreditCard card: userCardList.getReturnObject()){
            userPaymentRecord.addAll(paymentDAO.findPaymentsByNumber(card.getNumber()));
        }
        return userPaymentRecord;

    }
}
