package com.OptimistBank.BankApp.Service;

import com.OptimistBank.BankApp.Dto.TransactionDto;
import com.OptimistBank.BankApp.Entity.Transaction;
import com.OptimistBank.BankApp.Repository.TransactionRepo;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepo transactionRepo;

    public TransactionServiceImpl(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @Override
    public void saveTransaction(TransactionDto transaction) {
        Transaction newTransaction=Transaction.builder()
                .transactionType(transaction.getAccountNumber())
                .accountNumber(transaction.getAccountNumber())
                .amount(transaction.getAmount())
                .build();
        transactionRepo.save(newTransaction);

    }
}
