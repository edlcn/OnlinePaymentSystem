package com.example.transaction.Serialization;

import com.example.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Not necessary atm.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTRes {

    List<Transaction> transactionList;
}
