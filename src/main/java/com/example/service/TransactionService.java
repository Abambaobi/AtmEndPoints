package com.example.service;

import com.example.model.TransferDto;
import com.example.response.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {


//    TransactionResponse transactionResponse;

    public ResponseEntity<TransactionResponse> transfer(TransferDto transferDto){

        var transactionResponse = TransactionResponse.builder()
                .transaction_status("Transfer Successful")
                .build();
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK) ;
    }
}
