# FinanceTracker – Personal Finance Management API

FinanceTracker is a secure RESTful API for managing personal finances. Users can register, log in, create income/expense categories, record transactions, and view monthly financial summaries. Built with Spring Boot and JWT authentication.

## ✨ Features

- **User Authentication** – JWT-based login and registration with password encryption (BCrypt)
- **Category Management** – Create categories with type (INCOME/EXPENSE); unique per user
- **Transaction Tracking** – Record transactions with amount, date, description, and category
- **Monthly Reports** – Get income, expense, and net balance for any month
- **Expense Breakdown** – View expenses grouped by category for a given period
- **Secure Endpoints** – All endpoints except login/register require a valid JWT

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 3** (Web, Security, Data JPA, Validation)
- **Spring Security** with JWT (io.jsonwebtoken)
- **PostgreSQL** (production) / **H2** (development/testing)
- **Lombok** – reduce boilerplate code
- **Maven** – build tool
- **Postman** – API testing

## 📁 Project Structure
