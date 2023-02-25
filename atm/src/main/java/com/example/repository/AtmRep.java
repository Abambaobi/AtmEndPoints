package com.example.repository;

import com.example.model.AtmUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtmRep extends JpaRepository<AtmUser, Long> {
    Optional<AtmUser> findByUsername(String username);
    void deleteByUsername(String username);

}
