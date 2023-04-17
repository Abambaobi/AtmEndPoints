package com.example.configuration;

import com.example.model.AtmUser;
import com.example.repository.AtmRep;
import com.example.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class SpringSecConfig2 {

//    public String bank = new LoginDTO().getBank();

    @Autowired
    public JwtService jwtService;
    @Autowired
    public AtmRep atmRep;


    @Bean
    public UserDetailsService userDetailsService(){
        return  new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                String username = jwtService.extSubject(jwt);
//                bank = jwtService.claims(jwt).get("bank", String.class);
//                switch (bank){
//
//                    case "ACCESS":
                        Optional<AtmUser> atmUser = atmRep.findByUsername(username);
                        if (atmUser.isPresent()) {
                              return atmUser.get();
                        }
//                        break;
//
//                    case "GT":
//                        Optional<GT> gtUser = gtRep.findByUsername(username);
//                        if (gtUser.isPresent()) {
//                            return gtUser.get();
//                        }
//                        break;
//
//                    case "ZENITH":
//                        Optional<Zenith> zenithUser = zenithRep.findByUsername(username);
//                        if (zenithUser.isPresent()) {
//                            return zenithUser.get();
//                        }
//                        break;
//                }

                throw new UsernameNotFoundException("You don't have a Bank account");
            }

        };
    }


    @Bean
    public AuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider authenticate = new DaoAuthenticationProvider();
        authenticate.setUserDetailsService(userDetailsService());
        authenticate.setPasswordEncoder(passEnc());
        return authenticate;
    };

    @Bean
    public AuthenticationManager authenticationManage(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passEnc(){
        return new BCryptPasswordEncoder();
    }
}
