package com.example.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CardDetails {

    @Transient
    Random random = new Random();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long atmCardDetails;

//    by default it will create a foreign key column called Atm_User_id in this card details class
    @OneToOne(cascade = CascadeType.ALL)
    public AtmUser atmUser;


    private final String sixteenDigit = generateSeventeenDigits();
    private final String accNumber = generateAccountNumber();

    private final String cvv = generateCvv();

    private final LocalDate CardExpiringDate = LocalDate.now().plusYears(5);

    public String generateSeventeenDigits() {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 16; i++) {
            String randomNumber = String.valueOf(random.nextInt (10));
            map.put(i, randomNumber);
        }
       return (map.get(0) + map.get(1) +  map.get(2) +  map.get(3) +  map.get(4) +  map.get(5)
               +  map.get(6)+  map.get(7)+  map.get(8)+  map.get(9)+  map.get(10)
               +  map.get(11)+  map.get(12)+  map.get(13)+  map.get(14)+  map.get(15));
    }

    public String generateAccountNumber() {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            String randomNumber = String.valueOf(random.nextInt (10));
            map.put(i, randomNumber);
        }
        return (map.get(0) + map.get(1) +  map.get(2) +  map.get(3) +  map.get(4) +  map.get(5)
                +  map.get(6)+  map.get(7)+  map.get(8)+  map.get(9));
    }

    public String generateCvv() {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            String randomNumber = String.valueOf(random.nextInt (10));
            map.put(i, randomNumber);
        }
        return(map.get(0) + map.get(1) +  map.get(2));
    }

}
