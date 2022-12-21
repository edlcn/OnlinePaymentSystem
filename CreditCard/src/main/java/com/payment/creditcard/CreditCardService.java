package com.payment.creditcard;


import com.payment.creditcard.Serialization.CreditCard;
import com.payment.creditcard.Serialization.CreditCardDTReq;
import com.payment.creditcard.Serialization.SessionIdDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class CreditCardService {

    private final CreditCardDAO creditCardDAO;
    private final RestTemplate restTemplate;

    public CreditCardService(CreditCardDAO creditCardDAO, RestTemplate restTemplate) {
        this.creditCardDAO = creditCardDAO;
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "credit_cards",key = "#uid")
    public List<CreditCard> getCards(Long uid) {
        //Integer tokenValidityCheck = restTemplate.postForObject("http://localhost:8085/api/v1/session/validate",sessionId,Integer.class);
        //if (tokenValidityCheck == null || tokenValidityCheck == 0) throw new IllegalStateException("INVALID TOKEN!");

        log.info("Went database to fetch cards for user with id {}.",uid);
        return creditCardDAO.findCreditCardsByUserId(uid);
    }

    @CacheEvict(value = "credit_cards",key = "#creditCard.userId")
    public void addCard(CreditCardDTReq creditCard, SessionIdDTO sessionId) {
        Integer tokenValidityCheck = restTemplate.postForObject("http://session:8080/api/v1/session/validate",sessionId,Integer.class);
        if (tokenValidityCheck == null || tokenValidityCheck == 0) throw new IllegalStateException("INVALID TOKEN!");
        if (tokenValidityCheck == -1) throw new IllegalStateException("TOKEN EXPIRED!");

        log.info("Credit card cache wiped for user {}.",creditCard.getUserId());


        Integer validation = restTemplate.postForObject("http://credit-card-db:8080/classified/ccdb/validate",creditCard.getCreditCard(),Integer.class);
        if (validation == null || validation.equals(0)) throw new IllegalStateException("INVALID CREDIT CARD DATA!");
        if (creditCardDAO.existsByNumber(creditCard.getCreditCard().getNumber())) throw new IllegalStateException("CARD ALREADY IN USE!");
        CreditCard toBeAdded = new CreditCard(creditCard);
        creditCardDAO.save(toBeAdded);

        log.info("User with id {} added a new card with number {}.",creditCard.getUserId(),creditCard.getCreditCard().getNumber());

    }

    @Transactional
    @CacheEvict(value = "credit_cards",key = "#creditCard.userId")
    public void deleteCard(CreditCardDTReq creditCard, SessionIdDTO sessionId) {
        Integer tokenValidityCheck = restTemplate.postForObject("http://session:8080/api/v1/session/validate",sessionId,Integer.class);
        if (tokenValidityCheck == null || tokenValidityCheck == 0) throw new IllegalStateException("INVALID TOKEN!");
        if (tokenValidityCheck == -1) throw new IllegalStateException("TOKEN EXPIRED!");

        log.info("Credit card cache wiped for user {}.",creditCard.getUserId());
        CreditCard toBeDeleted = creditCardDAO.findCreditCardByNumber(creditCard.getCreditCard().getNumber());
        if (toBeDeleted == null) throw new IllegalStateException("CARD NOT FOUND!");
        //todo: Silinmeyecek inaktif yapılacak. Sonra ayarla.
        creditCardDAO.delete(toBeDeleted);
        log.info("Card with number {} is deleted.",creditCard.getCreditCard().getNumber());

    }
}
