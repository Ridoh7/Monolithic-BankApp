package com.OptimistBank.BankApp.Repository;

import com.OptimistBank.BankApp.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, String> {
}
