package com.example.model;
import com.example.role_bank.Bank;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotNull @NotBlank
    private String username;

    @Size(max=20, min = 4) @NotNull @NotBlank
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Bank bank;
}
