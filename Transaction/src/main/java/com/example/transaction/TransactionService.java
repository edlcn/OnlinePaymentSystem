package com.example.transaction;

import com.example.transaction.Serialization.SessionIdDTO;
import com.example.transaction.Serialization.TransactionDTReq;
import com.example.transaction.Serialization.TransactionDTRes;
import com.example.transaction.Serialization.TransactionValidationDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionDAO transactionDAO;
    private final RestTemplate restTemplate;

    public TransactionService(TransactionDAO transactionDAO, RestTemplate restTemplate) {
        this.transactionDAO = transactionDAO;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void addTransaction(TransactionDTReq transaction, SessionIdDTO sessionId) {
        Integer tokenValidityCheck = restTemplate.postForObject("http://session:8080/api/v1/session/validate",sessionId,Integer.class);
        if (tokenValidityCheck == null || tokenValidityCheck == 0) throw new IllegalStateException("INVALID TOKEN!");
        if (tokenValidityCheck == -1) throw new IllegalStateException("TOKEN EXPIRED!");

        //todo: Usera gidilecek balance takası yapılacak Integer dönecek Integer kontrol edilecek olmadıysa error fırlatılacak error yoksa
        //yeni transaction oluşturulup eklenecek. gidilecek user uri'si gatewaye tanıtılmamalı.

        TransactionValidationDTO transactionValidation = restTemplate.postForObject(
                "http://users:8080/api/v1/users/classified/transaction",
                transaction,
                TransactionValidationDTO.class);

        if (transactionValidation == null || transactionValidation.getStatus() <= 0) throw new IllegalStateException("TRANSACTION FAILED!");

        Transaction transactionToBeAdded = new Transaction(transaction, transactionValidation.getReceiverId(),transactionValidation.getName());
        transactionDAO.save(transactionToBeAdded);

    }

    public List<Transaction> getUserTransactions(Long uid, SessionIdDTO sessionId) {
        Integer tokenValidityCheck = restTemplate.postForObject("http://session:8080/api/v1/session/validate",sessionId,Integer.class);
        if (tokenValidityCheck == null || tokenValidityCheck == 0) throw new IllegalStateException("INVALID TOKEN!");
        if (tokenValidityCheck == -1) throw new IllegalStateException("TOKEN EXPIRED!");

        return transactionDAO.findTransactionsBySenderIdOrReceiverId(uid,uid);
    }
}
