package com.example.deposit;

import com.example.deposit.Serialization.DepositAmount;
import com.example.deposit.Serialization.DepositDTReq;
import com.example.deposit.Serialization.PaymentDTReq;
import com.example.deposit.Serialization.SessionIdDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DepositService {

    private final DepositDAO depositDAO;
    private final RestTemplate restTemplate;

    public DepositService(DepositDAO depositDAO, RestTemplate restTemplate) {
        this.depositDAO = depositDAO;
        this.restTemplate = restTemplate;
    }

    //todo: deposit errorlarını yetersiz bakiye ve invalid card diye ayır.
    @Transactional   //ne olur ne olmaz gereksiz olabilir.
    public void addDeposit(DepositDTReq deposit, SessionIdDTO sessionId) {
        Integer tokenValidityCheck = restTemplate.postForObject("http://session:8080/api/v1/session/validate",sessionId,Integer.class);
        if (tokenValidityCheck == null || tokenValidityCheck == 0) throw new IllegalStateException("INVALID TOKEN!");
        if (tokenValidityCheck == -1) throw new IllegalStateException("TOKEN EXPIRED!");

        PaymentDTReq depositToBeValidated = new PaymentDTReq(deposit.getCard(), deposit.getAmount());
        Integer paymentAuth = restTemplate.postForObject("http://credit-card-db:8080/classified/ccdb/payment",depositToBeValidated,Integer.class);
        if (paymentAuth == null || paymentAuth == 0) throw new IllegalStateException("DEPOSIT FAILED!");

        //userms'e req at balance'ı güncelle.
        Integer balanceUpgrade = restTemplate.postForObject(
                "http://users:8080/api/v1/users/classified/"+deposit.getDepositorId()+"/addDeposit",
                new DepositAmount(deposit.getAmount()),
                Integer.class);

        if (balanceUpgrade == null || balanceUpgrade == 0) throw new IllegalStateException("USER BALANCE COULD NOT BE UPDATED!");  // rollback gerekli.

        Deposit depositToBeAdded = new Deposit(deposit);
        depositDAO.save(depositToBeAdded);
    }

    public List<Deposit> getUserDeposits(Long uid, SessionIdDTO sessionId) {
        Integer tokenValidityCheck = restTemplate.postForObject("http://session:8080/api/v1/session/validate",sessionId,Integer.class);
        if (tokenValidityCheck == null || tokenValidityCheck == 0) throw new IllegalStateException("INVALID TOKEN!");
        if (tokenValidityCheck == -1) throw new IllegalStateException("TOKEN EXPIRED!");

        if (!uid.equals(sessionId.getId())) throw new IllegalStateException("YOU DONT HAVE ACCESS TO THIS INFORMATION!");

        return depositDAO.findDepositsByDepositorId(uid);
    }
}
