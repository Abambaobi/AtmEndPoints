package com.example.service;
import com.example.model.AtmUserCardDetails;
import com.example.repository.AtmRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class AtmUserCardDetails_Service {

    @Autowired
    AtmRep atmRep;

    public AtmUserCardDetails findCardDetailsByUsername(String username){
        List<Object[]> allDetails = atmRep.findByEmail (username);

        Map<String, AtmUserCardDetails> map = new HashMap<>();

        for(Object[] user : allDetails){

            //            Saving AtmUser Details

            AtmUserCardDetails atmUserCardDetails = new AtmUserCardDetails();

            atmUserCardDetails.setAccountBal((Long)user[1]);
            atmUserCardDetails.setBank((String) user[2]);
            atmUserCardDetails.setEmail((String) user[3]);
            atmUserCardDetails.setFirstname((String) user[4]);
            atmUserCardDetails.setLastname((String) user[5]);
            atmUserCardDetails.setPhone((String) user[7]);
            atmUserCardDetails.setPin((Integer) user[8]);
            atmUserCardDetails.setRole((String) user[9]);
            atmUserCardDetails.setUsername((String) user[10]);

            //            Saving CardDetails

            atmUserCardDetails.setCardExpiringDate(LocalDate.parse(String.valueOf(user[13])));
            atmUserCardDetails.setAccountnumber((String) user[14]);
            atmUserCardDetails.setCvv((String) user[15]);
            atmUserCardDetails.setSixteenDigit((String) user[16]);
            map.put("atmUserCardDetails", atmUserCardDetails);

        }
        return (map.get("atmUserCardDetails"));
    }

    public AtmUserCardDetails findAtmUserByAccountnumber(String accountnumber){
        List<Object[]> allDetails = atmRep.findByAccountnumber (accountnumber);

        Map<String, AtmUserCardDetails> map = new HashMap<>();

        for(Object[] user : allDetails){

            //            Saving AtmUser Details

            AtmUserCardDetails atmUserCardDetails = new AtmUserCardDetails();

            atmUserCardDetails.setAccountBal((Long)user[1]);
            atmUserCardDetails.setBank((String) user[2]);
            atmUserCardDetails.setEmail((String) user[3]);
            atmUserCardDetails.setFirstname((String) user[4]);
            atmUserCardDetails.setLastname((String) user[5]);
            atmUserCardDetails.setPhone((String) user[7]);
            atmUserCardDetails.setPin((Integer) user[8]);
            atmUserCardDetails.setRole((String) user[9]);
            atmUserCardDetails.setUsername((String) user[10]);

            //            Saving CardDetails

            atmUserCardDetails.setCardExpiringDate(LocalDate.parse(String.valueOf(user[13])));
            atmUserCardDetails.setAccountnumber((String) user[14]);
            atmUserCardDetails.setCvv((String) user[15]);
            atmUserCardDetails.setSixteenDigit((String) user[16]);
            map.put("atmUserCardDetails", atmUserCardDetails);

        }
        return (map.get("atmUserCardDetails"));
    }
}
