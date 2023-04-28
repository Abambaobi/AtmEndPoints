package com.example.controller;
import com.example.model.AtmUser;
import com.example.repository.AtmRep;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

    @Autowired
    AtmRep atmRep;

    @GetMapping(value = "/findoneuser")
    public ResponseEntity<AtmUser> getusers(@RequestParam("user") String user){
        Optional<AtmUser> val = atmRep.findByUsername(user);
        if(val.isPresent()){
            return new ResponseEntity<>(val.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    @Transactional
    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteusers (@RequestParam("user") List<String> user){
        for(String username : user){
            atmRep.deleteByUsername(username);
        }
        return new ResponseEntity<String>("Selected Users have been Deleted", HttpStatus.OK);
    }

    @GetMapping(value = "seeallusers")
    public ResponseEntity<List> findAll(){
        List<AtmUser> res = atmRep.findAll();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
