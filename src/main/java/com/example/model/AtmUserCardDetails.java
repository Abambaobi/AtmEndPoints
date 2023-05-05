package com.example.model;;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AtmUserCardDetails {

//    AtmUser Details

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private String phone;

    private int pin;

    private Long accountBal;

    private String role;

    private String bank;

//    CardDetails

    private String sixteenDigit;

    private String accountnumber;

    private String cvv;

    private LocalDate CardExpiringDate;
}
