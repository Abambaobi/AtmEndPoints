package com.example.configuration;
import com.example.model.AtmUser;
import com.example.repository.AtmRep;
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

    @Autowired
    public AtmRep atmRep;

    @Bean
    public UserDetailsService userDetailsService(){
        return  new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                        Optional<AtmUser> atmUser = atmRep.findByUsername(username);
                        if (atmUser.isPresent()) {
                              return atmUser.get();
                        }

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
