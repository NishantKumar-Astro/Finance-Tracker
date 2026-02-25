package com.example.FinanceTracker.Service;

import com.example.FinanceTracker.Model.Category;
import com.example.FinanceTracker.Model.Transaction;
import com.example.FinanceTracker.Model.Users;
import com.example.FinanceTracker.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    public List<Transaction>
    getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction
    getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction createTransaction(TransactionRequest request) {

        // 1. Find or validate category
        Category category = categoryService.findByNameAndType(
                request.getCategoryName(),
                request.getCategoryType()
        );
        if (category == null) {
            throw new RuntimeException("Category not found: " + request.getCategoryName());
        }

        // 2. Find user
        Users user = userService.getUserById(request.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found: " + request.getUserId());
        }

        // 3. Build transaction
        Transaction transaction = new Transaction();
        transaction.setDescription(request.getDescription());
        transaction.setAmount(request.getAmount());
        if (request.getTransactionDate() != null)
            transaction.setTransactiondate(request.getTransactionDate());
        transaction.setCategory(category);
        transaction.setUsers(user);

        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, TransactionRequest request) {
        Transaction transaction = getTransactionById(id);
        if (transaction == null) {
            return null;
        }

        // Update only fields that are present in the request (if using partial updates)
        if (request.getDescription() != null) {
            transaction.setDescription(request.getDescription());
        }
        if (request.getAmount() != null) {
            transaction.setAmount(request.getAmount());
        }
        if (request.getTransactionDate() != null) {
            transaction.setTransactiondate(request.getTransactionDate());
        }

        // Handle category update if provided
        if (request.getCategoryName() != null && request.getCategoryType() != 0) {
            Category category = categoryService.findByNameAndType(
                    request.getCategoryName(), request.getCategoryType());
            if (category == null) {
                throw new RuntimeException("Category not found");
            }
            transaction.setCategory(category);
        }

        // Handle user update if provided (maybe not allowed after creation)
        if (request.getUserId() != null) {
            Users user = userService.getUserById(request.getUserId());
            if (user == null) {
                throw new RuntimeException("User not found");
            }
            transaction.setUsers(user);
        }

        return transactionRepository.save(transaction);
    }

    public boolean
    deleteTransaction(Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Transaction> getTransactionByUserid(Long id) {
        return transactionRepository.findByUsersId(id);
    }
}