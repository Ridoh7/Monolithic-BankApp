package com.OptimistBank.BankApp.Controller;

import com.OptimistBank.BankApp.Dto.Response;
import com.OptimistBank.BankApp.Dto.TransactionRequest;
import com.OptimistBank.BankApp.Dto.TransferRequest;
import com.OptimistBank.BankApp.Dto.UserRequest;
import com.OptimistBank.BankApp.Service.UserService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Banking Application",
                description = "Spring Boot Banking Application REST API's Documentation",
                contact = @Contact(
                        name = "Ridoh",
                        email = "ridohlawal96@gmail.com",
                        url = "www.testingspringboot.com"
                ),license = @License(
                name = "Apache 2.0",
                url = "www.testingspringboot.com"
        )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Banking Application REST API's Documentation",
                url = "www.testingspringboot.com"
        )
)
@Tag(
        name = "User Account Service REST APIs/Endpoint",
        description = "Endpoints for Manipulating User Account"
)
public class UserController {
private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<Response>registerUser(@RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.registerUser(userRequest), HttpStatus.CREATED);
    }
    @GetMapping
    public List<Response>allUser(){
        return userService.allUser();
    }
    @GetMapping("/{userId}")
    public ResponseEntity<Response>fetchUser(@PathVariable(name = "userId") Long userId) {
        return userService.fetchUser(userId);
    }
    @GetMapping("/balEnquiry")
    public ResponseEntity<Response>balanceEnquiry(@RequestParam(name = "accountBalance") String accountNumber){
        return userService.balanceEnquiry(accountNumber);
    }
    @GetMapping("/nameEnquiry")
    public ResponseEntity<Response>nameEnquiry(@RequestParam(name = "accountName") String accountNumber){
        return userService.nameEnquiry(accountNumber);
    }
    @PutMapping("/credit")
    public ResponseEntity<Response>credit(@RequestBody TransactionRequest transactionRequest){
        return userService.credit(transactionRequest);
    }
    @PutMapping("/debit")
    public ResponseEntity<Response>debit(@RequestBody TransactionRequest transactionRequest){
        return userService.debit(transactionRequest);
    }
    @PutMapping("/transfer")
    public ResponseEntity<Response>transfer(@RequestBody TransferRequest transferRequest){
        return userService.transfer(transferRequest);
    }
}
