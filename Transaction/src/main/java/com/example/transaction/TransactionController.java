package com.example.transaction;


import com.example.transaction.Serialization.GeneralHttpResponse;
import com.example.transaction.Serialization.SessionIdDTO;
import com.example.transaction.Serialization.TransactionDTReq;
import com.example.transaction.Serialization.TransactionDTRes;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @CrossOrigin
    @PostMapping
    public GeneralHttpResponse<String> addTransaction(@RequestBody TransactionDTReq transaction,
                                                      @RequestHeader("id") Long id,
                                                      @RequestHeader("sessionId") String sessionId){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        SessionIdDTO IncSessionId = new SessionIdDTO(id,sessionId);
        try {
            transactionService.addTransaction(transaction,IncSessionId);
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;

    }

    @CrossOrigin
    @GetMapping
    public GeneralHttpResponse<List<Transaction>> getUserTransactions(@RequestParam Long uid,
                                                                           @RequestHeader("id") Long id,
                                                                           @RequestHeader("sessionId") String sessionId){
        GeneralHttpResponse<List<Transaction>> response = new GeneralHttpResponse<>("200",null);
        SessionIdDTO IncSessionId = new SessionIdDTO(id,sessionId);
        try{
            response.setReturnObject(transactionService.getUserTransactions(uid,IncSessionId));
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;
    }
}
