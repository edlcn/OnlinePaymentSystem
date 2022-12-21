package com.example.deposit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositDAO extends JpaRepository<Deposit,Long> {

    List<Deposit> findDepositsByDepositorId(Long depositorId);
}
