package com.sentinel.backend.transaction.service;


import com.sentinel.backend.transaction.entity.Transaction;
import com.sentinel.backend.transaction.repository.TransactionRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final TransactionRepository repository;

  public TransactionService(TransactionRepository repository) {
    this.repository = repository;
  }

  public List<Transaction> getAllTransactions() {
    return repository.findAll();
  }

  public Transaction saveTransaction(Transaction transaction) {
    return repository.save(transaction);
  }
}