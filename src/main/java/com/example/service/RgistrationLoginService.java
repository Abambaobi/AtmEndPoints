package com.example.service;

import com.example.response.AuthenticationResponse;
import com.example.model.AtmUser;
import com.example.model.CardDetails;
import com.example.model.LoginDTO;
import com.example.model.RegistrationDTO;
import com.example.repository.AtmRep;
import com.example.repository.CardRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RgistrationLoginService {

    @Autowired
    AtmRep atmRep;

    @Autowired
    CardRep cardRep;
    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public JwtService jwtService;
    @Autowired
    public PasswordEncoder passwordEncoder;


    public ResponseEntity<AuthenticationResponse> registerAtmUser(RegistrationDTO regDTO){

        AtmUser atmUser = regDTO.getAccessUser();
        Optional<AtmUser> dbUser = atmRep.findByUsername(atmUser.getUsername());


        if(dbUser.isPresent()){
            var authenticationResponse = AuthenticationResponse.builder()
                    .Registeration_Status("User Already Exist")
                    .Login_Status("Not logged in yet")
                    .JWT("Register and Login to get a token")
                    .build();

            return new ResponseEntity<AuthenticationResponse>(authenticationResponse, HttpStatus.BAD_REQUEST);
        }
        atmUser.setPassword(passwordEncoder.encode(atmUser.getPassword()));
        CardDetails cardDetails = new CardDetails();
        cardRep.save(cardDetails);

        atmUser.setCardDetails(cardDetails);
        atmRep.save(atmUser);


        var authenticationResponse = AuthenticationResponse.builder()
                .Registeration_Status("Registered Successfully")
                .Login_Status("Not logged in yet")
                .JWT("Login to get a token")
                .build();

        return new ResponseEntity<AuthenticationResponse>(authenticationResponse, HttpStatus.OK);
    }


    public  ResponseEntity<AuthenticationResponse> loginAtmUser(LoginDTO loginDTO, AtmUser atmUser){
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword(),
                    atmUser.getAuthorities()
            );
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);;
            Map<String,Object> extraClaims = new HashMap<>();
            extraClaims.put("firstname", atmUser.getFirstname());
            extraClaims.put("lastname", atmUser.getLastname());
            extraClaims.put("role", atmUser.getRole());
            extraClaims.put("phone", atmUser.getPhone());
            extraClaims.put("bank", atmUser.getBank());

            String jwt = jwtService.generateJwt(atmUser, extraClaims);
            var authenticationResponse = AuthenticationResponse.builder()
                    .Registeration_Status("Registered User")
                    .Login_Status("You are now logged in")
                    .JWT(jwt)
                    .build();

            return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

}
