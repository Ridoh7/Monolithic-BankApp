package com.OptimistBank.BankApp.Service;

import com.OptimistBank.BankApp.Dto.Response;
import com.OptimistBank.BankApp.Dto.TransactionRequest;
import com.OptimistBank.BankApp.Dto.TransferRequest;
import com.OptimistBank.BankApp.Dto.UserRequest;
import com.OptimistBank.BankApp.Email.dto.EmailDetails;
import com.OptimistBank.BankApp.Entity.AlertType;
import com.OptimistBank.BankApp.Entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    Response registerUser (UserRequest userRequest);
    List<Response>allUser();
    ResponseEntity<Response>fetchUser(Long userId);
    ResponseEntity<Response>balanceEnquiry(String accountNumber);
    ResponseEntity<Response>nameEnquiry(String accountNumber);
    ResponseEntity<Response>credit(TransactionRequest transactionRequest );
    ResponseEntity<Response>debit(TransactionRequest transactionRequest);
    ResponseEntity<Response>transfer(TransferRequest transferRequest);
    EmailDetails emailDetails(AlertType alert, AlertType alert1, User user, TransactionRequest transactionRequest);
}
