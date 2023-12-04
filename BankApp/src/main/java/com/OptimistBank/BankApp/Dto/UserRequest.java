package com.OptimistBank.BankApp.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String firstName;
    private String lastName;
    private String otherName;
    private String gender;
    private BigDecimal accountBalance;
    private String status;
    private String stateOfOrigin;
    private String address;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String email;
    @JsonIgnore
    private LocalDate dateOfBirth;
}
