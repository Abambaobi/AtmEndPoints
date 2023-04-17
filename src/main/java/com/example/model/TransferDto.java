package com.example.model;

import com.example.role_bank.Bank;
import com.example.role_bank.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Date;

public class TransferDto {

    String sixteenDig;

    Date expDate;

    String cvv;

    String pin;

    String amount;

    @Enumerated(EnumType.STRING)
    private Role role;

   String accNumber;
    @Enumerated(EnumType.STRING)
    private Bank bank;

}
