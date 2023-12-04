package com.OptimistBank.BankApp.Repository;

import com.OptimistBank.BankApp.Dto.Response;
import com.OptimistBank.BankApp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByAccountNumber(String AccountNumber);
    User findByAccountNumber(String AccountNumber);
}
