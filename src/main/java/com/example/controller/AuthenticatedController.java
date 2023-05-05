package com.example.controller;
import com.example.model.*;
import com.example.response.ExpiredJwtResponse;
import com.example.response.TransactionResponse;
import com.example.service.AtmUserCardDetails_Service;
import com.example.service.JwtService;
import com.example.repository.AtmRep;
import com.example.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticatedController {
    @Autowired
    AtmRep atmRep;

    @Autowired
    JwtService jwtService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    AtmUserCardDetails_Service atmUserCardDetails_service;

    @GetMapping(value = "/logout")
    public ResponseEntity<ExpiredJwtResponse> logout(HttpServletRequest request) throws Exception {

        String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        String username = jwtService.extSubject(jwt);
        SecurityContextHolder.clearContext();
        if(username != null){

            String expired_token = jwtService.expireToken(jwt);
            var expiredJWT = ExpiredJwtResponse.builder()
                    .expiredJWT(expired_token)
                    .build();

            return new ResponseEntity<>(expiredJWT, HttpStatus.OK);
        }

        var expiredJWT = ExpiredJwtResponse.builder()
                .expiredJWT("JWT subject is null")
                .build();
        return new ResponseEntity<>(expiredJWT, HttpStatus.BAD_REQUEST);

    };


    @SneakyThrows
    @GetMapping(value = "/home")
    public ResponseEntity<AtmUserCardDetails> details(HttpServletRequest request){


        String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        String username = jwtService.extSubject(jwt);
        Optional<AtmUser> atmUser = atmRep.findByUsername(username);

        if(atmUser.isPresent()  && jwtService.validateJwt(atmUser.get(), jwt)){
            AtmUserCardDetails loggedInUserCard =  atmUserCardDetails_service.findCardDetailsByUsername(username);

            return new ResponseEntity<>(loggedInUserCard, HttpStatus.OK);
        }

        return new ResponseEntity<>(new AtmUserCardDetails(), HttpStatus.BAD_REQUEST);

    }

    @CrossOrigin
    @PostMapping(value = "/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferDTO transferDTO, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        String username = jwtService.extSubject(jwt);

        Optional<AtmUser> atmUserDetails = atmRep.findByUsername(username);
        AtmUserCardDetails atmUserCardDetails =  atmUserCardDetails_service.findCardDetailsByUsername(username);

        if (atmUserDetails.isPresent() && (atmUserDetails.get().getBank() == transferDTO.getBank())) {

                if(Long.valueOf(transferDTO.getAmount()) <= atmUserDetails.get().getAccountBal()){
                    return transactionService.transfer(atmUserDetails.get(), atmUserCardDetails, transferDTO);
             }
            var transactionResponse = TransactionResponse.builder()
                    .transaction_status("Insufficient account balance")
                    .build();
            return new ResponseEntity<>(transactionResponse, HttpStatus.OK) ;
        }
        var transactionResponse = TransactionResponse.builder()
                .transaction_status("User does not exist")
                .build();
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK) ;

    }

    @PostMapping(value = "/withdraw")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody WithdrawDTO withdrawDTO, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        String username = jwtService.extSubject(jwt);

        Optional<AtmUser> atmUser1 = atmRep.findByUsername(username);

        if (atmUser1.isPresent() && (atmUser1.get().getBank() == withdrawDTO.getBank())) {
            AtmUser atmUser = atmUser1.get();
            AtmUserCardDetails atmUserCardDetails =  atmUserCardDetails_service.findCardDetailsByUsername(username);

            if(Long.valueOf(withdrawDTO.getAmount()) <= atmUser.getAccountBal()){
                return transactionService.withdraw(atmUser, atmUserCardDetails, withdrawDTO);

            }
            var transactionResponse = TransactionResponse.builder()
                    .transaction_status("Insufficient account balance")
                    .build();
            return new ResponseEntity<>(transactionResponse, HttpStatus.OK) ;

        }
        var transactionResponse = TransactionResponse.builder()
                .transaction_status("User does not exist")
                .build();
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK) ;

    }


    @PostMapping(value = "/deposit")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody DepositDTO depositDTO, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        String username = jwtService.extSubject(jwt);

        Optional<AtmUser> atmUser1 = atmRep.findByUsername(username);

        if (atmUser1.isPresent() && (atmUser1.get().getBank() == depositDTO.getBank())) {
            AtmUserCardDetails atmUserCardDetails =  atmUserCardDetails_service.findCardDetailsByUsername(username);
            AtmUser atmUser = atmUser1.get();
            return transactionService.deposit(atmUser, atmUserCardDetails, depositDTO);
        }
        var transactionResponse = TransactionResponse.builder()
                .transaction_status("User does not exist")
                .build();
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK) ;

    }

    }


