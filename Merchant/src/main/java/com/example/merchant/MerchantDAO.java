package com.example.merchant;

import com.example.merchant.virtualPos.VirtualPos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MerchantDAO extends JpaRepository<Merchant,Long> {

    boolean existsByMail(String mail);

    Merchant findMerchantById(Long id);

}
