package com.OptimistBank.BankApp.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String otherName;
    private String gender;
    private String accountNumber;
    private BigDecimal accountBalance;
    private String status;
    private String stateOfOrigin;
    private String address;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    @CreationTimestamp
    private String createdAt;
    @UpdateTimestamp
    private String modifiedAt;

}
