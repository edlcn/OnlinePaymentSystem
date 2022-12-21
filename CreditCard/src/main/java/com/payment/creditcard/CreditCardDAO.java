package com.payment.creditcard;

import com.payment.creditcard.Serialization.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardDAO extends JpaRepository<CreditCard,String> {

    boolean existsByUserId(Long UserId);

    boolean existsByNumber(String number);

    List<CreditCard> findCreditCardsByUserId(Long userId);

    CreditCard findCreditCardByNumber(String number);
}
