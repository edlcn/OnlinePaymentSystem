package com.example.deposit;

import com.example.deposit.Serialization.DepositDTReq;
import com.example.deposit.Serialization.GeneralHttpResponse;
import com.example.deposit.Serialization.SessionIdDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/deposit")
public class DepositController {

    private final DepositService depositService;


    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @CrossOrigin
    @PostMapping
    public GeneralHttpResponse<String> addDeposit(@RequestBody DepositDTReq deposit,
                                                  @RequestHeader("id") Long id,
                                                  @RequestHeader("sessionId") String sessionId){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        SessionIdDTO IncSessionId = new SessionIdDTO(id,sessionId);
        try {
            depositService.addDeposit(deposit,IncSessionId);
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;

    }

    @CrossOrigin
    @GetMapping
    public GeneralHttpResponse<List<Deposit>> getUserDeposits(@RequestParam Long uid,
                                                              @RequestHeader("id") Long id,
                                                              @RequestHeader("sessionId") String sessionId){
        GeneralHttpResponse<List<Deposit>> response = new GeneralHttpResponse<>("200",null);
        SessionIdDTO IncSessionId = new SessionIdDTO(id,sessionId);
        try {
            response.setReturnObject(depositService.getUserDeposits(uid,IncSessionId));
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }



        return response;

    }


}
