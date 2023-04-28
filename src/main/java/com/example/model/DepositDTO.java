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
public class DepositDTO {
    private String sixteenDig;
    private LocalDate expDate;
    private String cvv;
    private int pin;
    private Long amount;
    private String username;
    @Enumerated(EnumType.STRING)
    private Bank bank;
    @Enumerated(EnumType.STRING)
    private Role role;

}
