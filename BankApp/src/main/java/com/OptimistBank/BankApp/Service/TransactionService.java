package com.OptimistBank.BankApp.Service;

import com.OptimistBank.BankApp.Dto.TransactionDto;

import java.util.List;

public interface TransactionService {
void saveTransaction(TransactionDto transactionDto);
//List<TransactionDto>fetchAllTransaction()
}
