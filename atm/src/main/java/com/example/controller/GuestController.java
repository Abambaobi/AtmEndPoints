package com.example.controller;
import com.example.model.AtmUser;
import com.example.model.LoginDTO;
import com.example.model.RegistrationDTO;
import com.example.repository.AtmRep;
import com.example.response.AuthenticationResponse;
import com.example.service.RgistrationLoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")

public class GuestController {
    @Autowired
    RgistrationLoginService service;

    @Autowired
    AtmRep atmRep;

    @PostMapping(value = "/open_account")
    public ResponseEntity<AuthenticationResponse> register (@Valid @RequestBody RegistrationDTO regDTO){
        return service.registerAtmUser(regDTO);
    }


    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> home (@Valid @RequestBody LoginDTO loginDTO) {

        Optional<AtmUser> dbUser = atmRep.findByUsername(loginDTO.getUsername());

        if (dbUser.isPresent() && dbUser.get().getBank().equals(loginDTO.getBank())){

                return service.loginAtmUser(loginDTO, dbUser.get());
            }

        var authenticationResponse = AuthenticationResponse.builder()
                .Login_Status("User does not exist-")
                .build();
        return new ResponseEntity<>(authenticationResponse, HttpStatus.BAD_REQUEST);
        }
}
