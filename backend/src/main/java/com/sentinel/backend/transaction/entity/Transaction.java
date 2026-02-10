package com.sentinel.backend.transaction.entity;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;
  private double amount;
  private String category;
  private LocalDate date;

  // Constructors
  public Transaction() {}

  public Transaction(String description, double amount, String category, LocalDate date) {
    this.description = description;
    this.amount = amount;
    this.category = category;
    this.date = date;
  }

  // Getters
  public Long getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public double getAmount() {
    return amount;
  }

  public String getCategory() {
    return category;
  }

  public LocalDate getDate() {
    return date;
  }

  // Setters
  public void setDescription(String description) {
    this.description = description;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }
}
