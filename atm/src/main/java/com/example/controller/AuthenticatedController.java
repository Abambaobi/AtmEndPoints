package com.example.controller;

import com.example.service.JwtService;
import com.example.repository.AtmRep;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/auth")
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


    @GetMapping(value = "/home")
    public String home (){
        return "welcome home";
    }

}
