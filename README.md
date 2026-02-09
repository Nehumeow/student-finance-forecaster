# student-finance-forecaster

# 🏦 Vantage: AI-Powered Student Liquidity Forecaster

![Status](https://img.shields.io/badge/Status-Prototype-green)
![Stack](https://img.shields.io/badge/Tech-Java_Spring_Boot_|_React_|_Python_AI-blue)
![Focus](https://img.shields.io/badge/Domain-Fintech-orange)

**Vantage** is a microservices-based financial dashboard designed to solve the "Student Liquidity Crisis." Unlike traditional budget apps that merely track *past* spending, Vantage uses a predictive Python engine to calculate **Financial Runway**—telling users exactly how many days remain until they hit 0 SEK based on their current burn rate.

---

## 🏗️ System Architecture & Design

This project implements a **Distributed Microservices Architecture** to separate concerns between Data Management, User Interface, and Predictive Logic.

### 1. High-Level Architecture
The system consists of three distinct services communicating via REST APIs.

```mermaid
graph TD
    User((User)) -->|Browser| Frontend[React Frontend<br/>Port: 5173]
    
    subgraph "Local Environment"
        Frontend -->|1. Fetch Transactions| Backend[Java Spring Boot<br/>Port: 8080]
        Frontend -->|2. Fetch Risk Analysis| AI[Python AI Engine<br/>Port: 5000]
        
        Backend <-->|Read/Write| DB[(H2 Database<br/>In-Memory)]
        AI -->|3. Request Data for Math| Backend
    end


sequenceDiagram
    participant U as User (Browser)
    participant R as React Frontend
    participant J as Java Backend
    participant P as Python AI

    U->>R: Opens Dashboard
    par Parallel Requests
        R->>J: GET /api/transactions
        J-->>R: Returns JSON List
    and
        R->>P: GET /predict
        P->>J: GET /api/transactions (Internal Fetch)
        J-->>P: Returns Raw Data
        P->>P: Calculate Burn Rate & Days Left
        P-->>R: Returns Prediction (JSON)
    end
    R->>U: Renders Table & Warning Box



classDiagram
    class Transaction {
        -Long id
        -String description
        -double amount
        -LocalDate date
        +getAmount()
        +setAmount()
    }

    class TransactionRepository {
        <<Interface>>
        +findAll()
        +save()
    }

    class TransactionController {
        +getTransactions()
        +addTransaction()
    }

    class DataLoader {
        +run()
    }

    TransactionController --> TransactionRepository : Uses
    DataLoader --> TransactionRepository : Populates
    TransactionRepository --> Transaction : Manages
