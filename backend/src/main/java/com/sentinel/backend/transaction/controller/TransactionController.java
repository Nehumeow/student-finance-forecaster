package com.sentinel.backend.transaction.controller;


import com.sentinel.backend.transaction.entity.Transaction; // <--- IMPORTS FROM ENTITY
import com.sentinel.backend.transaction.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {

  private final TransactionService service;

  public TransactionController(TransactionService service) {
    this.service = service;
  }

  @GetMapping
  public List<Transaction> getTransactions() {
    return service.getAllTransactions();
  }

  @PostMapping
  public Transaction addTransaction(@RequestBody Transaction transaction) {
    return service.saveTransaction(transaction);
  }
}