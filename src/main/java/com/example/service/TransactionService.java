package com.example.service;
import com.example.model.*;
import com.example.repository.AtmRep;
import com.example.repository.CardRep;
import com.example.response.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    AtmRep atmRep;

    @Autowired
    AtmUserCardDetails_Service atmUserCardDetails_service;

//    TransactionResponse transactionResponse;

    public ResponseEntity<TransactionResponse> transfer(AtmUser atmUserDetails,
                                                        AtmUserCardDetails atmUserCardDetails,
                                                        TransferDTO transferDTO){

        //        Check if card details correspond

        if(
            atmUserCardDetails.getSixteenDigit().equals(transferDTO.getSixteenDig()) &&
//            atmUserCardDetails.getCardExpiringDate().equals(transferDTO.getExpDate()) &&
            atmUserCardDetails.getCvv().equals(transferDTO.getCvv()) &&
            atmUserCardDetails.getPin() == transferDTO.getPin()
        )
     {
        //         Get the recipient details

         AtmUserCardDetails atmRecipientUserCardDetails = atmUserCardDetails_service.findAtmUserByAccountnumber(transferDTO.getAccNumber());
         Optional<AtmUser>  atmRecipientUser = atmRep.findByUsername(atmRecipientUserCardDetails.getUsername());

            //         if the recipient with the account number exist
        if (atmRecipientUser.isPresent() &&
                atmRecipientUserCardDetails.getBank().equals(transferDTO.getRecepientBank())
        ) {

            //            subtract the amount from sender's account
            atmUserDetails.setAccountBal( atmUserDetails.getAccountBal() - transferDTO.getAmount() );
            atmRep.save(atmUserDetails);

            //            save the new account balance to recipient account
            AtmUser atmRecipientUser1 = atmRecipientUser.get();
            atmRecipientUser1.setAccountBal( atmRecipientUser1.getAccountBal() + transferDTO.getAmount() );
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
         return new ResponseEntity<>(transactionResponse, HttpStatus.BAD_REQUEST);
     }
//        Else if card details does not correspond

        var transactionResponse = TransactionResponse.builder()
                .transaction_status("Invalid sender's card details")
                .build();
        return new ResponseEntity<>(transactionResponse, HttpStatus.BAD_REQUEST);
    }

//    Transfer ends here


//    withdraw starts here
    public ResponseEntity<TransactionResponse> withdraw (AtmUser atmUser,
                                                         AtmUserCardDetails atmUserCardDetails ,
                                                         WithdrawDTO withdrawDTO){

//        Check if card details correspond

        if(
                atmUserCardDetails.getSixteenDigit().equals(withdrawDTO.getSixteenDig()) &&
//                atmUserCardDetails.getCardExpiringDate().equals(withdrawDTO.getExpDate()) &&
                atmUserCardDetails.getCvv().equals(withdrawDTO.getCvv()) &&
                atmUser.getPin() == withdrawDTO.getPin()

        )
        {
//            save the new account balance
            atmUser.setAccountBal( atmUser.getAccountBal() - withdrawDTO.getAmount() );
            atmRep.save(atmUser);

        var transactionResponse = TransactionResponse.builder()

                .transaction_status("Withdraw Successful")
                .build();
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK) ;}

//        Else if card details does not correspond

        var transactionResponse = TransactionResponse.builder()
                .transaction_status("Invalid card details")
                .build();
        return new ResponseEntity<>(transactionResponse, HttpStatus.BAD_REQUEST);
    }
//    Withdraw ends


//    Deposit starts here
    public ResponseEntity<TransactionResponse> deposit (AtmUser atmUser,
                                                        AtmUserCardDetails atmUserCardDetails ,
                                                        DepositDTO depositDTO){

//        Check if card details correspond

        if(
                atmUserCardDetails.getSixteenDigit().equals(depositDTO.getSixteenDig()) &&
//                atmUserCardDetails.getCardExpiringDate().equals(depositDTO.getExpDate()) &&
                atmUserCardDetails.getCvv().equals(depositDTO.getCvv()) &&
                atmUser.getPin() == depositDTO.getPin()
        )
        {
            //            save the new account balance
            atmUser.setAccountBal( atmUser.getAccountBal() + depositDTO.getAmount() );
            atmRep.save(atmUser);
            var transactionResponse = TransactionResponse.builder()

                    .transaction_status("Deposit Successful")
                    .build();
            return new ResponseEntity<>(transactionResponse, HttpStatus.OK) ;}

//        Else if card details does not correspond

        var transactionResponse = TransactionResponse.builder()
                .transaction_status("Invalid card details")
                .build();
        return new ResponseEntity<>(transactionResponse, HttpStatus.BAD_REQUEST);
    }
//    Deposit ends
}
