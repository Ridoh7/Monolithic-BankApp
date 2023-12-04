package com.OptimistBank.BankApp.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transaction;
    private String transactionType;
    private BigDecimal amount;
    private String accountNumber;
    @CreationTimestamp
    private LocalDateTime dateTime;
}
