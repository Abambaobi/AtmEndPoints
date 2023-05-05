package com.example.service;
import com.example.model.*;
import com.example.repository.AtmRep;
import com.example.response.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    AtmRep atmRep;

    @Autowired
    AtmUserCardDetails_Service atmUserCardDetails_service;

    public ResponseEntity<TransactionResponse> transfer(AtmUser atmUserDetails,
                                                        AtmUserCardDetails atmUserCardDetails,
                                                        TransferDTO transferDTO){

        //        Check if card details correspond

        if(
            atmUserCardDetails.getSixteenDigit().equals(transferDTO.getSixteenDig()) &&
            atmUserCardDetails.getCardExpiringDate().equals(transferDTO.getExpDate()) &&
            atmUserCardDetails.getCardExpiringDate().isAfter(LocalDate.now()) &&
            atmUserCardDetails.getCvv().equals(transferDTO.getCvv()) &&
            atmUserCardDetails.getPin() == Integer.valueOf(transferDTO.getPin())
        )
     {
        //         Get the recipient details

         AtmUserCardDetails atmRecipientUserCardDetails = atmUserCardDetails_service.findAtmUserByAccountnumber(transferDTO.getAccNumber());
         Optional<AtmUser>  atmRecipientUser = atmRep.findByUsername(atmRecipientUserCardDetails.getUsername());

            //         if the recipient with the account number exist
        if (atmRecipientUser.isPresent() &&
            atmRecipientUserCardDetails.getBank().equals(transferDTO.getRecepientBank()) &&
            !(atmUserCardDetails.getAccountnumber().equals("Incorrect"))
        ) {

            //            subtract the amount from sender's account
            atmUserDetails.setAccountBal( atmUserDetails.getAccountBal() - Long.valueOf(transferDTO.getAmount()) );
            atmRep.save(atmUserDetails);

            //            save the new account balance to recipient account
            AtmUser atmRecipientUser1 = atmRecipientUser.get();
            atmRecipientUser1.setAccountBal( atmRecipientUser1.getAccountBal() + Long.valueOf(transferDTO.getAmount()) );
            atmRep.save(atmRecipientUser1);
                 var transactionResponse = TransactionResponse.builder()
                .transaction_status("Transfer Successful")
                .build();
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
                }
            //         elif the recipient with the account number does not exist

         var transactionResponse = TransactionResponse.builder()
                 .transaction_status("invalid recipient account number")
                 .build();
         return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
     }
//        Else if card details does not correspond

        var transactionResponse = TransactionResponse.builder()
                .transaction_status("Invalid sender's card details")
                .build();
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

//    Transfer ends here


//    withdraw starts here
    public ResponseEntity<TransactionResponse> withdraw (AtmUser atmUser,
                                                         AtmUserCardDetails atmUserCardDetails ,
                                                         WithdrawDTO withdrawDTO){

//        Check if card details correspond

        if(
                atmUserCardDetails.getSixteenDigit().equals(withdrawDTO.getSixteenDig()) &&
                atmUserCardDetails.getCardExpiringDate().equals(withdrawDTO.getExpDate()) &&
                atmUserCardDetails.getCardExpiringDate().isAfter(LocalDate.now()) &&
                atmUserCardDetails.getCvv().equals(withdrawDTO.getCvv()) &&
                atmUser.getPin() == Integer.valueOf(withdrawDTO.getPin())

        )
        {
//            save the new account balance
            atmUser.setAccountBal( atmUser.getAccountBal() - Long.valueOf(withdrawDTO.getAmount()) );
            atmRep.save(atmUser);

        var transactionResponse = TransactionResponse.builder()

                .transaction_status("Withdraw Successful")
                .build();
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK) ;}

//        Else if card details does not correspond

        var transactionResponse = TransactionResponse.builder()
                .transaction_status("Invalid card details")
                .build();
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }
//    Withdraw ends


//    Deposit starts here

    public ResponseEntity<TransactionResponse> deposit (AtmUser atmUser,
                                                        AtmUserCardDetails atmUserCardDetails ,
                                                        DepositDTO depositDTO){
//        Check if card details correspond

        if(
                atmUserCardDetails.getSixteenDigit().equals(depositDTO.getSixteenDig()) &&
                atmUserCardDetails.getCardExpiringDate().equals(depositDTO.getExpDate()) &&
                atmUserCardDetails.getCardExpiringDate().isAfter(LocalDate.now()) &&
                atmUserCardDetails.getCvv().equals(depositDTO.getCvv()) &&
                atmUser.getPin() == Integer.valueOf(depositDTO.getPin())
        )
        {
            //            save the new account balance
            atmUser.setAccountBal( atmUser.getAccountBal() + Long.valueOf(depositDTO.getAmount()) );
            atmRep.save(atmUser);
            var transactionResponse = TransactionResponse.builder()

                    .transaction_status("Deposit Successful")
                    .build();
            return new ResponseEntity<>(transactionResponse, HttpStatus.OK) ;}

//        Else if card details does not correspond

        var transactionResponse = TransactionResponse.builder()
                .transaction_status("Invalid card details")
                .build();
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }
//    Deposit ends
}
