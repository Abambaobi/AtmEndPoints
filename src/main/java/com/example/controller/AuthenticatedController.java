package com.example.controller;

import com.example.model.AtmUser;
import com.example.model.TransferDto;
import com.example.response.ExpiredJwtResponse;
import com.example.response.TransactionResponse;
import com.example.service.JwtService;
import com.example.repository.AtmRep;
import com.example.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<AtmUser> details(HttpServletRequest request){


        String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        String username = jwtService.extSubject(jwt);
        Optional<AtmUser> atmUser = atmRep.findByUsername(username);
        AtmUser loggedInUser = atmUser.get();

        if(atmUser.isPresent()  && jwtService.validateJwt(atmUser.get(), jwt)){
            System.out.println(jwtService.validateJwt(atmUser.get(), jwt));
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(loggedInUser, HttpStatus.BAD_REQUEST);

    }

    @SneakyThrows
    @GetMapping(value = "/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferDto transferDto){

            return transactionService.transfer(transferDto);

    }

}
