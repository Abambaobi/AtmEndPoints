package com.example.controller;

import com.example.model.AtmUser;
import com.example.service.JwtService;
import com.example.repository.AtmRep;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticatedController {
    @Autowired
    AtmRep atmRep;

    @Autowired
    JwtService jwtService;

    @GetMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) throws Exception {

        String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        String username = jwtService.extSubject(jwt);
        SecurityContextHolder.clearContext();
        if(username != null){
            return new ResponseEntity<>(jwtService.expireToken(jwt), HttpStatus.OK);
        }
        return new ResponseEntity<>("JWT subject is null", HttpStatus.BAD_REQUEST);
    };


//    @GetMapping(value = "/home")
//    public String home (){
//        return "welcome home";
//    }

    @SneakyThrows
    @GetMapping(value = "/home")
    public ResponseEntity<AtmUser> details(HttpServletRequest request){


        String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        String username = jwtService.extSubject(jwt);
        Optional<AtmUser> atmUser = atmRep.findByUsername(username);
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "GET");
//        response.setHeader("Access-Control-Allow-Headers", "*");
        if(atmUser.isPresent()){

            return new ResponseEntity<>(atmUser.get(), HttpStatus.OK);
        }
        throw new Exception("invalid user");
    }
}
