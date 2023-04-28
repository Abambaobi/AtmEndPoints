package com.example.model;
import com.example.role_bank.Bank;
import com.example.role_bank.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {

    private String sixteenDig;

    private LocalDate expDate;

    private String cvv;

    private int pin;

    private Long amount;

    private String recepientBank;

    private String accNumber;

    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Bank bank;



}
