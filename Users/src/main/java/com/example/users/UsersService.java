package com.example.users;

import com.example.users.Serialization.*;
import com.example.users.confirmationToken.ConfirmationToken;
import com.example.users.confirmationToken.ConfirmationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@Slf4j
public class UsersService {

    private final UsersDAO usersDAO;
    private final ConfirmationTokenService confirmationTokenService;

    private final RestTemplate restTemplate;

    public UsersService(UsersDAO usersDAO, ConfirmationTokenService confirmationTokenService, RestTemplate restTemplate) {
        this.usersDAO = usersDAO;
        this.confirmationTokenService = confirmationTokenService;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void register(UsersDTReq user) {
        if(usersDAO.findByMail(user.getMail()) != null) throw new IllegalStateException("MAIL IN USE!");

        Users toBeCreated = new Users(user);
        Users DbIns = usersDAO.save(toBeCreated);

        ConfirmationToken confirmationToken = new ConfirmationToken(DbIns);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        ConfirmationMail sentObject = new ConfirmationMail(user.getMail(),"http://mkb.express.edlcn/validate?confirmationToken="+confirmationToken.getToken());

        Integer mailSent = restTemplate.postForObject("http://mail:8080/classified/mail/registration",sentObject,Integer.class);

        if (mailSent == null || mailSent == 0) throw new IllegalStateException("CONFIRMATION MAIL COULD NOT BE SENT!");  //ROLLBACK


    }

    public SessionIdDTOWithBalance login(Login login) {
        Users user = usersDAO.findByMail(login.getMail());
        if (user == null) throw new IllegalStateException("USER NOT FOUND!");
        if (!user.getPassword().equals( login.getPassword())) throw new IllegalStateException("WRONG PASSWORD!");
        if(!user.isActive()) throw new IllegalStateException("ACCOUNT NOT YET ACTIVATED!");
        SessionIdDTO sessionId = restTemplate.postForObject("http://session:8080/api/v1/session/create/"+user.getId().toString(),null,SessionIdDTO.class);
        SessionIdDTOWithBalance loginReturnObject = new SessionIdDTOWithBalance(sessionId,user.getBalance());
        return loginReturnObject;

    }

    // !!!!! Internal communicationda port verilmezse 504 -> CrossOrigine rağmen !!!!!
    public void logout(SessionIdDTO sessionId) {
        Integer tokenValidityCheck = restTemplate.postForObject("http://session:8080/api/v1/session/validate",sessionId,Integer.class);
        if (tokenValidityCheck == null || tokenValidityCheck == 0) throw new IllegalStateException("INVALID TOKEN!");
        if (tokenValidityCheck == -1) throw new IllegalStateException("TOKEN EXPIRED!");

        Integer response = restTemplate.postForObject("http://session:8080/api/v1/session/delete",sessionId,Integer.class);
        if (response == null || response == 0) throw new IllegalStateException("USER ALREADY LOGGED OUT!"); // trivial


    }

    @Transactional
    public Integer addDeposit(Long uid, DepositAmount depositAmount) {

        Users user = usersDAO.findUsersById(uid);
        if (user == null) throw new IllegalStateException("USER NOT FOUND!");

        user.setBalance(user.getBalance()+depositAmount.getAmount());
        return 1;

    }

    @Transactional
    public TransactionValidationDTO transactionValidation(TransactionDTReq transaction) {
        TransactionValidationDTO response = new TransactionValidationDTO(0,null,null);

        Users sender = usersDAO.findUsersById(transaction.getSenderId());  //trivial check if sender is null
        Users receiver = usersDAO.findByMail(transaction.getReceiverMail());

        if (receiver == null) return response;
        if (sender.getBalance() < transaction.getAmount()){
            response.setStatus(-1);
            return response;
        }

        sender.setBalance(sender.getBalance() - transaction.getAmount());
        receiver.setBalance(receiver.getBalance() + transaction.getAmount());

        response.setReceiverId(receiver.getId());
        response.setName(sender.getName());
        response.setStatus(1);
        return response;

    }

    public SessionIdDTOWithBalance loginToPay(LoginWithPaymentToken login) {
        Users user = usersDAO.findByMail(login.getMail());
        if (user == null) throw new IllegalStateException("USER NOT FOUND!");
        if (!user.getPassword().equals( login.getPassword())) throw new IllegalStateException("WRONG PASSWORD!");
        if(!user.isActive()) throw new IllegalStateException("ACCOUNT NOT YET ACTIVATED!");
        SessionIdDTO sessionId = restTemplate.postForObject("http://session:8080/api/v1/session/create/"+user.getId().toString(),null,SessionIdDTO.class);
        if (sessionId == null) throw new IllegalStateException("SESSION ID COULD NOT BE CREATED!");
        SetUserDTO setUserDTO = new SetUserDTO(user.getId(), login.getToken());
        Integer userSetResponse = restTemplate.postForObject("http://payment:8080/api/v1/merchantPayment/setUser",setUserDTO,Integer.class);
        if (userSetResponse == null || userSetResponse < 0) throw new IllegalStateException("USER SET FOR PAYMENT FAILED!");
        return new SessionIdDTOWithBalance(sessionId,user.getBalance());
    }

    public UserDTRes getUserDetails(Long id) {
        Users user =  usersDAO.findUsersById(id);
        return new UserDTRes(user.getName());

    }

    public UserMailDTRes getUserMail(Long id) {
        Users user = usersDAO.findUsersById(id);
        return new UserMailDTRes(user.getMail());
    }

    public Double getUserBalance(Long id,SessionIdDTO sessionId) {
        Integer tokenValidityCheck = restTemplate.postForObject("http://session:8080/api/v1/session/validate",sessionId,Integer.class);
        if (tokenValidityCheck == null || tokenValidityCheck == 0) throw new IllegalStateException("INVALID TOKEN!");
        if (tokenValidityCheck == -1) throw new IllegalStateException("TOKEN EXPIRED!");

        Users user  = usersDAO.findUsersById(id);
        return user.getBalance();
    }
}
