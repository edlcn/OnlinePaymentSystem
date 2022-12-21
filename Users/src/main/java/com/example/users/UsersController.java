package com.example.users;


import com.example.users.Serialization.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin
public class UsersController {

    private final UsersService usersService;


    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }



    @PostMapping("/register")
    public GeneralHttpResponse<String> register(@RequestBody UsersDTReq user){

        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try{
            usersService.register(user);
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;
    }


    @PostMapping("/login")
    public GeneralHttpResponse<SessionIdDTOWithBalance> login(@RequestBody Login login){
        GeneralHttpResponse<SessionIdDTOWithBalance> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(usersService.login(login));
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;




    }


    @PostMapping("/logout")
    public GeneralHttpResponse<String> logout(@RequestBody SessionIdDTO sessionId){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try{
            usersService.logout(sessionId);
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;
    }

    //todo:logout, error kodlarına göre return code -> clientin gerekli tepkileri verebilmesi için.
    // örneğin expiry date doldu dönen koda göre çıkış yapacak.



    @PostMapping("/classified/{uid}/addDeposit")
    public Integer addDeposit(@PathVariable Long uid,@RequestBody DepositAmount depositAmount){

        return usersService.addDeposit(uid,depositAmount);
    }


    @PostMapping("classified/transaction")
    public TransactionValidationDTO transactionValidation(@RequestBody TransactionDTReq transaction){
        return usersService.transactionValidation(transaction);
    }


    @PostMapping("/paymentLogin")
    public GeneralHttpResponse<SessionIdDTOWithBalance> loginToPay(@RequestBody LoginWithPaymentToken login){
        GeneralHttpResponse<SessionIdDTOWithBalance> response = new GeneralHttpResponse<>("200",null);
        try {
            response.setReturnObject(usersService.loginToPay(login));
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;
    }


    @GetMapping("{id}")
    public UserDTRes getUserDetails(@PathVariable Long id){
        return usersService.getUserDetails(id);
    }


    @GetMapping("/mail/{id}")
    public UserMailDTRes getUserMail(@PathVariable Long id){
        return usersService.getUserMail(id);
    }


    @GetMapping("/balance/{uid}")
    public GeneralHttpResponse<Double> getUserBalance(@PathVariable Long uid,
                                           @RequestHeader("id") Long id,
                                           @RequestHeader("token") String token){
        SessionIdDTO sessionId = new SessionIdDTO(id,token);
        GeneralHttpResponse<Double> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(usersService.getUserBalance(uid,sessionId));
        }
        catch (Exception e){
            response.setStatus("400:"+e.getMessage());
        }
        return response;
    }

}
