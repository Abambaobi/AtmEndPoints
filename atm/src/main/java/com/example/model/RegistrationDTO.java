package com.example.model;
import com.example.role_bank.Bank;
import com.example.role_bank.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RegistrationDTO {

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Bank bank;

    public AtmUser getAccessUser() {
        AtmUser atmUser = new AtmUser();
        atmUser.setFirstname(this.firstname);
        atmUser.setUsername(this.username);
        atmUser.setRole(this.role);
        atmUser.setLastname(this.lastname);
        atmUser.setPhone(this.phone);
        atmUser.setEmail(this.email);
        atmUser.setPassword(this.password);
        atmUser.setBank(this.bank);
        return atmUser;
    }
}
