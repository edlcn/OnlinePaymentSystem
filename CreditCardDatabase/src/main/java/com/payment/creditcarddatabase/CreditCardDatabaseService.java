package com.payment.creditcarddatabase;


import com.payment.creditcarddatabase.Serialization.CreditCardDTO;
import com.payment.creditcarddatabase.Serialization.PaymentDTReq;
import com.payment.creditcarddatabase.Serialization.VirtualPostPaymentDTReq;
import com.payment.creditcarddatabase.virtualPosDatabase.VirtualPosDatabaseDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
public class CreditCardDatabaseService {

    private final CreditCardDatabaseDAO creditCardDatabaseDAO;
    private final VirtualPosDatabaseDAO virtualPosDatabaseDAO;

    public CreditCardDatabaseService(CreditCardDatabaseDAO creditCardDatabaseDAO, VirtualPosDatabaseDAO virtualPosDatabaseDAO) {
        this.creditCardDatabaseDAO = creditCardDatabaseDAO;
        this.virtualPosDatabaseDAO = virtualPosDatabaseDAO;
    }


    public CreditCardDatabase getCard(Long id) {
        return creditCardDatabaseDAO.findCreditCardDatabaseById(id);

    }

    public void addCard(CreditCardDatabase ccdb) {
        creditCardDatabaseDAO.save(ccdb);
    }

    public Integer validateCard(CreditCardDTO card) {

        CreditCardDatabase cardDB = creditCardDatabaseDAO.findCreditCardDatabaseByNumber(card.getNumber());


        if (cardDB == null) return 0;

        if (!cardDB.equals(card)) return 0;

        return 1;
    }

    @Transactional
    public Integer paymentAuth(PaymentDTReq payment) {
        if(validateCard(payment.getCreditCard()) == 0) return 0;
        CreditCardDatabase cardDB = creditCardDatabaseDAO.findCreditCardDatabaseByNumber(payment.getCreditCard().getNumber());
        if(cardDB.getBalance() < payment.getPrice()) return 0;
        cardDB.setBalance(cardDB.getBalance()-payment.getPrice());
        return 1;

    }

    @Transactional
    public Integer virtualPosPaymentAuth(VirtualPostPaymentDTReq payment) {
        if (!virtualPosDatabaseDAO.existsById(payment.getVirtualPosId())) return -1;
        if (validateCard(payment.getCreditCard()) == 0) return -2;
        CreditCardDatabase cardDB = creditCardDatabaseDAO.findCreditCardDatabaseByNumber(payment.getCreditCard().getNumber());
        if(cardDB.getBalance() < payment.getAmount()) return -3;

        cardDB.setBalance(cardDB.getBalance()-payment.getAmount());

        CreditCardDatabase receiver = creditCardDatabaseDAO
                .findCreditCardDatabaseByIban(virtualPosDatabaseDAO
                        .findVirtualPosDatabaseById(payment.getVirtualPosId()).getRelatedAccountIban());

        receiver.setBalance(receiver.getBalance() + payment.getAmount());

        return 1;



    }
}
