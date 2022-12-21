package com.example.payment.merchantPayment;


import com.example.payment.CreditCard;
import com.example.payment.Serialization.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MerchantPaymentService {

    private final MerchantPaymentDAO merchantPaymentDAO;
    private final RestTemplate restTemplate;

    public MerchantPaymentService(MerchantPaymentDAO merchantPaymentDAO, RestTemplate restTemplate) {
        this.merchantPaymentDAO = merchantPaymentDAO;
        this.restTemplate = restTemplate;
    }

    public String merchantPaymentRequest(MerchantPaymentDTReq merchantPayment) {
        // todo merchant var mı diye bak.
        MerchantPayment IncomingPayment = new MerchantPayment(merchantPayment);
        merchantPaymentDAO.save(IncomingPayment);
        return IncomingPayment.getToken();
    }

    public MerchantPaymentDTRes merchantPaymentPage(String token) {
        MerchantPayment paymentDb = merchantPaymentDAO.findMerchantPaymentByToken(token);
        //VposId vposId = restTemplate.getForObject("http://mkb.express.edlcn/api/v1/merchant/"+toBeSentBack.getMerchantId()+"/vpos",VposId.class);
        if (paymentDb == null) throw new IllegalStateException("PAYMENT TOKEN NOT FOUND!");

        UserDTRes username = restTemplate.getForObject("http://users:8080/api/v1/users/"+paymentDb.getUid(),UserDTRes.class);
        if(username == null) throw new IllegalStateException("USERNAME NOT FOUND!");


        return new MerchantPaymentDTRes(paymentDb.getAmount(), paymentDb.getName(),username.getName());
    }

    @Transactional
    public void completePayment(CreditCard creditCard, String token) {

        MerchantPayment paymentDb = merchantPaymentDAO.findMerchantPaymentByToken(token);

        if (paymentDb == null) throw new IllegalStateException("PAYMENT TOKEN NOT FOUND!");


        VposId vposId = restTemplate.getForObject("http://merchant:8080/api/v1/merchant/"+paymentDb.getMerchantId()+"/vpos",VposId.class);

        if (vposId == null || vposId.getVposId() == null) throw new IllegalStateException("VPOS FETCH FAILED!");


        MerchantPaymentFinalization merchantPaymentFinalization = new MerchantPaymentFinalization(creditCard, paymentDb.getAmount(), vposId.getVposId());


        Integer paymentAuth = restTemplate.postForObject("http://credit-card-db:8080/classified/ccdb/vpPayment",merchantPaymentFinalization,Integer.class);

        if (paymentAuth == null || paymentAuth < 0) throw new IllegalStateException("PAYMENT FAILED!"+paymentAuth.toString());


        paymentDb.setCompleted(true);

        UserMailDTRes mailFetch = restTemplate.getForObject("http://users:8080/api/v1/users/mail/"+paymentDb.getUid(),UserMailDTRes.class);
        if (mailFetch == null) return;

        PaymentVerification paymentVerification = new PaymentVerification();
        paymentVerification.setMerchantName(paymentDb.getName());
        paymentVerification.setAmount(paymentDb.getAmount());
        paymentVerification.setTo(mailFetch.getMail());




        Integer mailSent = restTemplate.postForObject("http://mail:8080/classified/mail/payment",paymentVerification,Integer.class);





    }

    @Transactional
    public Integer setUser(SetUserDTO userDTO) {
        MerchantPayment merchantPayment = merchantPaymentDAO.findMerchantPaymentByToken(userDTO.getToken());
        if (merchantPayment == null) return -1;
        merchantPayment.setUid(userDTO.getUid());
        return 1;
    }

    public List<MerchantPaymentDTO> getUserMerchantPayments(Long uid) {
        List<MerchantPayment> merchantPayments = merchantPaymentDAO.findMerchantPaymentsByUid(uid);
        return merchantPayments.stream().map(MerchantPayment::convertToDTO).collect(Collectors.toCollection(ArrayList::new));

    }
}
