package com.OptimistBank.BankApp.Service;

import com.OptimistBank.BankApp.Dto.*;
import com.OptimistBank.BankApp.Email.dto.EmailDetails;
import com.OptimistBank.BankApp.Entity.AlertType;
import com.OptimistBank.BankApp.Entity.User;
import com.OptimistBank.BankApp.Repository.UserRepo;
import com.OptimistBank.BankApp.Util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepo userRepo;
    private final TransactionService transactionService;

    public UserServiceImpl(UserRepo userRepo, TransactionService transactionService) {
        this.userRepo = userRepo;
        this.transactionService = transactionService;
    }

    @Override
    public Response registerUser(UserRequest userRequest) {
        /**
         * check if user exists, if the usr doesn't exist return response,
         * generate account number
         * go ahead to save
         */
        boolean isUserExist=userRepo.existsByEmail(userRequest.getEmail());
        if (isUserExist){
            return Response.builder()
                    .responseCode(ResponseUtil.USER_EXIST_CODE)
                    .responseMessage(ResponseUtil.USER_EXIST_MESSAGE)
                    .data(null)
                    .build();

        }
        User user=User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .gender(userRequest.getGender())
                .accountBalance(userRequest.getAccountBalance())
                .accountNumber(ResponseUtil.generateAccountNumber(ResponseUtil.LENGTH_OF_ACCOUNT_NUMBER))
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .dateOfBirth(userRequest.getDateOfBirth())
                .address(userRequest.getAddress())
                .build();
        User savedUser=userRepo.save(user);

        EmailDetails emailDetails= EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("Account Creation")
                .messageBody("Congratulations! Your account has been successfully created.\n Your account details: " +
                        "Account Name: " +savedUser.getFirstName()+" "+ savedUser.getLastName()+" "+savedUser.getOtherName()+
                "\n Account number:" + savedUser.getAccountNumber()).build();

        return Response.builder()
                .responseCode(ResponseUtil.USER_SUCCESS_CODE)
                .responseMessage(ResponseUtil.USER_SUCCESS_MESSAGE)
                .data(String.valueOf(Data.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName()+" "+ savedUser.getLastName()+" "
                        + savedUser.getOtherName())
                        .build()))
                .build();
    }

    @Override
    public List<Response> allUser() {
        List<User>usersList=userRepo.findAll();
        List<Response>response=new ArrayList<>();

        for (User user: usersList) {
            response.add(Response.builder()
                    .responseCode(ResponseUtil.USER_SUCCESS_CODE)
                    .responseMessage(ResponseUtil.USER_SUCCESS_MESSAGE)
                    .data(String.valueOf(Data.builder()
                            .accountBalance(user.getAccountBalance())
                            .accountNumber(user.getAccountNumber())
                            .accountName(user.getFirstName()+" "+user.getLastName()+" "+user.getLastName())
                            .build()))
                    .build());

        }
        return response;
    }

    @Override
    public ResponseEntity<Response> fetchUser(Long userId) {
        if (!userRepo.existsById(userId)){
            return new ResponseEntity<>(Response.builder()
                    .responseMessage(ResponseUtil.USERID_NOT_FOUND_MESSAGE)
                    .responseCode(ResponseUtil.USER_ID_NOT_FOUND_CODE)
                    .data(null)
                    .build(), HttpStatus.NOT_FOUND);
        }
        User user=userRepo.findById(userId).get();
        return new ResponseEntity<>(Response.builder()
                .responseCode(ResponseUtil.USER_EXIST_CODE)
                .responseMessage(ResponseUtil.USER_EXIST_MESSAGE)
                .data(String.valueOf(Data.builder()
                        .accountNumber(user.getAccountNumber())
                        .accountBalance(user.getAccountBalance())
                        .accountName(user.getFirstName()+" "+user.getLastName()+" "+user.getOtherName())
                        .build()))
                .build(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> balanceEnquiry(String accountNumber) {
        boolean isAccountExists= userRepo.existsByAccountNumber(accountNumber);
        if (!userRepo.existsByAccountNumber(accountNumber)) {
            return new ResponseEntity<>(Response.builder()
                    .responseMessage(ResponseUtil.USERID_NOT_FOUND_MESSAGE)
                    .responseCode(ResponseUtil.USER_ID_NOT_FOUND_CODE)
                    .data(null)
                    .build(), HttpStatus.NOT_FOUND);
        }
            User user=userRepo.findByAccountNumber(accountNumber);
                return new ResponseEntity<>(Response.builder()
                        .responseCode(ResponseUtil.USER_EXIST_CODE)
                        .responseMessage(ResponseUtil.USER_EXIST_MESSAGE)
                        .data(String.valueOf(Data.builder()
                                .accountBalance(user.getAccountBalance())
                                .accountNumber(user.getAccountNumber())
                                .accountName(user.getFirstName()+" "+user.getLastName()+" "+user.getOtherName())
                                .build()))
                        .build(),HttpStatus.FOUND);
            }

    @Override
    public ResponseEntity<Response> nameEnquiry(String accountNumber) {
        boolean isAccountExist= userRepo.existsByAccountNumber(accountNumber);
        if (!isAccountExist) {
            return new ResponseEntity<>(Response.builder()
                    .responseMessage(ResponseUtil.USERID_NOT_FOUND_MESSAGE)
                    .responseCode(ResponseUtil.USER_ID_NOT_FOUND_CODE)
                    .data(null)
                    .build(), HttpStatus.NOT_FOUND);
        }

            User user=userRepo.findByAccountNumber(accountNumber);
            return new ResponseEntity<>(Response.builder()
                    .responseCode(ResponseUtil.USER_EXIST_CODE)
                    .responseMessage(ResponseUtil.USER_EXIST_MESSAGE)
                    .data(String.valueOf(Data.builder()
                            .accountNumber(user.getAccountNumber())
                            .accountBalance(user.getAccountBalance())
                            .accountName(user.getFirstName()+" "+user.getLastName()+" "+user.getOtherName())
                            .build()))
                    .build(),HttpStatus.FOUND);
        }

    @Override
    public ResponseEntity<Response> credit(TransactionRequest transactionRequest) {
        User receiver=userRepo.findByAccountNumber(transactionRequest.getAccountNumber());
        if (!userRepo.existsByAccountNumber(transactionRequest.getAccountNumber())) {
            return new ResponseEntity<>(Response.builder()
                    .responseCode(ResponseUtil.USER_ID_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtil.USERID_NOT_FOUND_MESSAGE)
                    .data(String.valueOf(Data.builder()
                            .accountNumber(transactionRequest.getAccountNumber())
                            .build()))
                    .build(), HttpStatus.NOT_FOUND);
        }

        receiver.setAccountBalance(receiver.getAccountBalance().add(transactionRequest.getAmount()));
        userRepo.save(receiver);

        TransactionDto transactionDto=new TransactionDto();
        transactionDto.setTransactionType("credit");
        transactionDto.setAccountNumber(transactionRequest.getAccountNumber());
        transactionDto.setAmount(transactionRequest.getAmount());

        transactionService.saveTransaction(transactionDto);

        return new ResponseEntity<>(Response.builder()
                .responseCode(ResponseUtil.USER_SUCCESS_CODE)
                .responseMessage(ResponseUtil.ACCOUNT_CREDITED_MESSAGE)
                .data(String.valueOf(Data.builder()
                        .accountNumber(receiver.getAccountNumber())
                        .accountBalance(receiver.getAccountBalance())
                        .accountName(receiver.getFirstName()+" "+receiver.getLastName()+" "+receiver.getOtherName())
                        .build()))
                .build(),HttpStatus.ACCEPTED);

    }

    @Override
    public ResponseEntity<Response> debit(TransactionRequest transactionRequest) {
        User debitingUser=userRepo.findByAccountNumber(transactionRequest.getAccountNumber());
        if (!userRepo.existsByAccountNumber(transactionRequest.getAccountNumber())){
            return new ResponseEntity<>(Response.builder()
                    .responseCode(ResponseUtil.USER_ID_NOT_FOUND_CODE)
                    .responseMessage(ResponseUtil.USERID_NOT_FOUND_MESSAGE)
                    .data(String.valueOf(Data.builder()
                            .accountNumber(transactionRequest.getAccountNumber())
                            .build()))
                    .build(),HttpStatus.NOT_FOUND);
        }
        if (debitingUser.getAccountBalance().compareTo(transactionRequest.getAmount())>0) {
            debitingUser.setAccountBalance(debitingUser.getAccountBalance().subtract(transactionRequest.getAmount()));

            userRepo.save(debitingUser);

            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setTransactionType("debit");
            transactionDto.setAccountNumber(debitingUser.getAccountNumber());
            transactionDto.setAmount(transactionRequest.getAmount());

            transactionService.saveTransaction(transactionDto);

            return new ResponseEntity<>(Response.builder()
                    .responseCode(ResponseUtil.USER_SUCCESS_CODE)
                    .responseMessage(ResponseUtil.ACCOUNT_DEBITED_MESSAGE)
                    .data(String.valueOf(Data.builder()
                            .accountNumber(debitingUser.getAccountNumber())
                            .accountBalance(debitingUser.getAccountBalance())
                            .accountName(debitingUser.getFirstName() + " " + debitingUser.getLastName() + " " + debitingUser.getOtherName())
                            .build()))
                    .build(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(Response.builder()
                .responseCode(ResponseUtil.USER_UNSUCCESSFUL_TRANSACTION_CODE)
                .responseMessage(ResponseUtil.USER_UNSUCCESSFUL_TRANSACTION_MESSAGE)
                .data(String.valueOf(Data.builder()
                        .accountBalance(debitingUser.getAccountBalance())
                        .accountNumber(debitingUser.getAccountNumber())
                        .accountName(debitingUser.getFirstName()+" "+debitingUser.getLastName()+" "+debitingUser.getOtherName())
                        .build()))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Response> transfer(TransferRequest transferRequest) {
        boolean receiverExist=userRepo.existsByAccountNumber(transferRequest.getDestinationAccountNumber());

        if (receiverExist){
            ResponseEntity<Response>debitResponse=debit(new TransactionRequest(transferRequest.getSourceAccountNumber(),
                    transferRequest.getAmount()));
            if (debitResponse.getStatusCode() !=HttpStatus.ACCEPTED){
                return debitResponse;
            }
            credit(new TransactionRequest(transferRequest.getDestinationAccountNumber(),transferRequest.getAmount()));
            //To display accountBalance of the receiver after successful transaction
            User receiverUser=userRepo.findByAccountNumber(transferRequest.getDestinationAccountNumber());
            BigDecimal destinationAccountBalance=receiverUser.getAccountBalance();

            return new ResponseEntity<>(Response.builder()
                    .responseCode(ResponseUtil.USER_SUCCESSFUL_TRANSFER_CODE)
                    .responseMessage(ResponseUtil.USER_SUCCESSFUL_TRANSFER_MESSAGE)
                    .data(String.valueOf(Data.builder()
                            .accountName(receiverUser.getFirstName()+" "+receiverUser.getLastName()+" "+receiverUser.getOtherName())
                            .accountBalance(destinationAccountBalance).build()))
                    .build(),HttpStatus.OK);
        }
        return new ResponseEntity<>(Response.builder()
                .responseMessage(ResponseUtil.USER_ID_NOT_FOUND_CODE)
                .responseCode(ResponseUtil.USERID_NOT_FOUND_MESSAGE)
                .data(String.valueOf(Data.builder()
                        .accountNumber(transferRequest.getDestinationAccountNumber())
                        .build()))
                .build(),HttpStatus.NOT_FOUND);
    }

    @Override
    public EmailDetails emailDetails(AlertType alert, AlertType alert1, User user,
                                     TransactionRequest transactionRequest) {
        return EmailDetails.builder()
                .recipient(user.getEmail())
                .subject(alert + "Alert")
                .messageBody(
                        "Dear" +user.getFirstName().toUpperCase()+" "
                                +user.getOtherName().toUpperCase()+" "
                                +user.getLastName().toUpperCase()+
                        ", your account has been" + alert1 + "with #"+transactionRequest.getAmount()+
                        "and your account balance is #"+ user.getAccountBalance()+"."+
                        "\n Please ignore this mail it is just an implementation of putting emailing" +
                                " feature in my banking app project."+"\n Best regards, Ridoh Lawal")
                .build();

    }
}



