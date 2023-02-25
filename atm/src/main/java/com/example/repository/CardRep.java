package com.example.repository;

import com.example.model.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRep extends JpaRepository<CardDetails, Long> {
}
