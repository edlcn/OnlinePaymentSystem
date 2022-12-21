package com.example.payment.merchantPayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantPaymentDAO extends JpaRepository<MerchantPayment,Long> {

    MerchantPayment findMerchantPaymentByToken(String token);
    List<MerchantPayment> findMerchantPaymentsByUid(Long id);
}
