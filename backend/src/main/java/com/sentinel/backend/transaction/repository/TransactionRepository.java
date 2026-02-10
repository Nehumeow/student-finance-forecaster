package com.sentinel.backend.transaction.repository;

import com.sentinel.backend.transaction.entity.Transaction; // <--- IMPORTS FROM ENTITY
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}