package com.example.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDAO extends JpaRepository<Transaction,Long> {

    List<Transaction> findTransactionsBySenderIdOrReceiverId(Long sid,Long rid);
}
