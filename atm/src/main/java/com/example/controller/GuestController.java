package com.example.controller;
import com.example.model.AtmUser;
import com.example.model.LoginDTO;
import com.example.model.RegistrationDTO;
import com.example.repository.AtmRep;
import com.example.response.AuthenticationResponse;
import com.example.service.RgistrationLoginService;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1")

public class GuestController {
    @Autowired
    RgistrationLoginService service;

    @Autowired
    AtmRep atmRep;
    @Autowired
    public AuthenticationResponse authenticationResponse;


    @SneakyThrows
    @PostMapping(value = "/open_account")
    public ResponseEntity<AuthenticationResponse> register (@Valid @RequestBody RegistrationDTO regDTO){

        return service.registerAtmUser(regDTO);
    }

    @SneakyThrows
    @PostMapping(value = "/home")
    public ResponseEntity<AuthenticationResponse> home (@Valid @RequestBody LoginDTO loginDTO) {

        Optional<AtmUser> access = atmRep.findByUsername(loginDTO.getUsername());

        if (access.isPresent() && access.get().getBank().equals(loginDTO.getBank())){

                return service.loginAtmUser(loginDTO, access.get());
            }

        var authenticationResponse = AuthenticationResponse.builder()
                .Login_Status("User does not exist-")
                .build();
        return new ResponseEntity<>(authenticationResponse, HttpStatus.BAD_REQUEST);
        }



}
