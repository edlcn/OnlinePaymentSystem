package com.payment.creditcarddatabase;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CreditCardDatabaseDAO extends JpaRepository<CreditCardDatabase,Long> {

    CreditCardDatabase findCreditCardDatabaseById(Long id);
    CreditCardDatabase findCreditCardDatabaseByNumber(String number);

    CreditCardDatabase findCreditCardDatabaseByIban(String iban);
}
