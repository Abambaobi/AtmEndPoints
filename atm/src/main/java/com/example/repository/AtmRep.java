package com.example.repository;

import com.example.model.AtmUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface AtmRep extends JpaRepository<AtmUser, Long> {
    Optional<AtmUser> findByUsername(String username);
    void deleteByUsername(String username);

    @Query(value = "SELECT * FROM atm_user JOIN card_details ON atm_user.cardid = card_details.cardid WHERE atm_user.username=:username", nativeQuery = true )
    List<Object[]> findByEmail(@Param("username") String username);

    @Query(value = "SELECT * FROM atm_user JOIN card_details ON atm_user.cardid = card_details.cardid WHERE card_details.accountnumber=:accountnumber", nativeQuery = true )
    List<Object[]> findByAccountnumber(@Param("accountnumber") String accountnumber);


}
