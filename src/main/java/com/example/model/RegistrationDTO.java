package com.example.model;
import com.example.role_bank.Bank;
import com.example.role_bank.Role;
import com.example.customAnnotation.NigerianNumber;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RegistrationDTO {


    private String username;

    @Size(max=20, min = 3) @NotNull @NotBlank
    private String firstname;

    @Size(max=20, min = 3) @NotNull @NotBlank
    private String lastname;

    @NotNull @NotBlank @Email
    private String email;

//    This is a custom annotation that only allows Nigerian numbers in this format +23407031635101
//    @NigerianNumber
//    private String phone;

    @Size(max=20, min = 4) @NotNull @NotBlank
    private String password;

    @Size(max=4, min = 4) @NotNull @NotBlank
    private String pin;
    @NotBlank @NotNull
    private String accountBal;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Bank bank;

    public AtmUser getAccessUser() {
        AtmUser atmUser = new AtmUser();
        atmUser.setFirstname(this.firstname);
        atmUser.setUsername(this.username);
        atmUser.setRole(this.role);
        atmUser.setLastname(this.lastname);
//        atmUser.setPhone(this.phone);
        atmUser.setEmail(this.email);
        atmUser.setPassword(this.password);
        atmUser.setBank(this.bank);
        atmUser.setPin(Integer.valueOf(this.pin));
        atmUser.setAccountBal(Long.valueOf(this.accountBal));
        return atmUser;
    }
}
