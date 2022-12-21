package com.example.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDAO extends JpaRepository<Payment,Long> {
    List<Payment> findPaymentsByNumber(String number);

}
