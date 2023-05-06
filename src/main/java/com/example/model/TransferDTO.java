package com.example.model;
import com.example.role_bank.Bank;
import com.example.role_bank.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Size(max=16, min = 16) @NotNull @NotBlank
    private String sixteenDig;

    @NotNull
    private LocalDate expDate;

    @Size(max=3, min = 3) @NotNull @NotBlank
    private String cvv;

    @Size(max=4, min = 4) @NotNull @NotBlank
    private String pin;

    @NotNull @NotBlank
    private String amount;

    @Size(max=6, min = 4) @NotNull @NotBlank
    private String recepientBank;

    @Size(max=10, min = 10) @NotNull @NotBlank
    private String accNumber;

    private String username;

   @NotNull
    @Enumerated(EnumType.STRING)
    private Bank bank;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;




}
