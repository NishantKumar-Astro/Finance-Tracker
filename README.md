# FinanceTracker – Personal Finance Management API
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Junit](https://img.shields.io/badge/junit-%23E33332?logo=junit5&logoColor=white)
![Railway](https://img.shields.io/badge/Railway-131415?style=for-the-badge&logo=railway&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellijidea&logoColor=white)
[![Java CI with Maven](https://github.com/NishantKumar-Astro/Finance-Tracker/actions/workflows/main.yml/badge.svg)](https://github.com/NishantKumar-Astro/Finance-Tracker/actions/workflows/main.yml)

FinanceTracker is a secure RESTful API for managing personal finances. Users can register, log in, create income/expense categories, record transactions, and view monthly financial summaries. Built with Spring Boot and JWT authentication.

## 🚄 Deployment Platform- Railway  (Currently on status Undeployed due to lack of money)                                              
https://finance-tracker-production-79c9.up.railway.app/swagger-ui/index.html#/                             

## ✨ Features

- **Testing** - Junit and Mockito  
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
FinanceTracker/               
├── src/main/java/com/example/FinanceTracker/                        
│ ├── config/ – Security and JWT filter configuration                                             
│ ├── controller/ – REST controllers (User, Category, Transaction, Report)                                     
│ ├── model/ – JPA entities (Users, Category, Transaction)                                      
│ ├── repository/ – Spring Data JPA repositories                               
│ └── service/ – Business logic (UserService, CategoryService, etc.)                                  
├── src/main/resources/                                  
│ └── application.properties – configuration                                    
└── pom.xml                                   


## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven
- PostgreSQL (optional, H2 works out-of-the-box)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/FinanceTracker.git
   cd FinanceTracker
2. Configure the database (skip for H2)                                     
  Edit src/main/resources/application.properties:                                      
    spring.datasource.url=jdbc:postgresql://localhost:5432/finance_db                             
    spring.datasource.username=yourusername                                   
    spring.datasource.password=yourpassword                                      
    spring.jpa.hibernate.ddl-auto=update                                   
3. Build and run                                   
     ```bash                                                      
     mvn clean install                                                 
     mvn spring-boot:run                                                            

The API will be available at http://localhost:8080.                                             

📋 API Endpoints                                                
Authentication                                               
Method	Endpoint	Description	Auth Required                                                
```POST	/api/users/register	Register new user	No                                            
POST	/api/users/login	Login & receive JWT	No                                     
```
Categories (JWT required)                                                  
Method	Endpoint	Description                                                     
```GET	/api/categories	Get all categories                                      
GET	/api/categories/{id}	Get category by ID                                          
POST	/api/categories	Create a new category                                      
PUT	/api/categories/{id}	Update category                                           
DELETE	/api/categories/{id}	Delete category                                        
```
Transactions (JWT required)
Method	Endpoint	Description                                                       
```GET	/api/transactions	Get all transactions                                            
GET	/api/transactions/{id}	Get transaction by ID                                             
GET	/api/transactions/user/{id}	Get transactions for a user                                             
POST	/api/transactions/create	Create a new transaction                                          
PUT	/api/transactions/update/{id}	Update transaction                                
DELETE	/api/transactions/delete/{id}	Delete transaction                                        
```
Reports (JWT required)
Method	Endpoint	Description                                          
```
GET	/api/reports/monthly/{userId}	Monthly summary (income, expense, balance)                                            
```
🧪 Sample Requests
Login:

```json
POST /api/users/login                                                  
{                                       
  "username": "john",                                         
  "password": "secret"                                       
}                                                                 
Response: eyJhbGciOiJIUzI1... (JWT token)                                         
```
Create Transaction:

```json
POST /api/transactions/create                                                
Authorization: Bearer <your-token>                                                   
{                                                                    
  "description": "Lunch",                                
  "amount": 25.50,                                             
  "transactionDate": "2025-03-01",                                        
  "categoryName": "Dining Out",                               
  "categoryType": 2,                                     
  "userId": 1                                   
}
```
🗂️ Database Schema:
Currently not available(coming soon)                      

🔮 Future Enhancements
Add pagination for transactions                                    

Implement budget limits per category                                   

Include email notifications                                 

Build a React frontend                              

🤝 Contributing                                           
Contributions are welcome! Feel free to open issues or submit pull requests.                     

📄 License
This project is for educational/demonstration purposes.                      

📬 Contact
Nishant Kuamar – infinityseeker@gmail.com
GitHub: NishantKumar-Astro
