package com.example.FinanceTracker.Service;

import com.example.FinanceTracker.Model.Transaction;
import com.example.FinanceTracker.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Transaction
    createTransaction(Transaction transaction) {
        transaction.setTransactiondate(LocalDate.now());
        // Validate category exists
        if (transaction.getCategory() != null
                &&
                transaction.getCategory().getId() != null) {
            transaction.setCategory
                    (categoryService.getCategoryById(transaction.getCategory().getId()));
        }

        // Validate user exists
        if (transaction.getUsers() != null
                &&
                transaction.getUsers().getId() != null) {
            transaction.setUsers
                    (userService.getUserById(transaction.getUsers().getId()));
        }

        return transactionRepository.save(transaction);
    }

    public Transaction
    updateTransaction(Long id, Transaction transactionDetails) {
        Transaction transaction = getTransactionById(id);
        if (transaction != null) {
            transaction.setDescription(transactionDetails.getDescription());
            transaction.setAmount(transactionDetails.getAmount());
            transaction.setTransactiondate(transactionDetails.getTransactiondate());

            // Update category if provided
            if (transactionDetails.getCategory() != null
                    &&
                    transactionDetails.getCategory().getId() != null) {
                transaction.setCategory
                        (categoryService.getCategoryById(transactionDetails.getCategory().getId()));
            }

            // Update user if provided
            if (transactionDetails.getUsers() != null
                    &&
                    transactionDetails.getUsers().getId() != null) {
                transaction.setUsers
                        (userService.getUserById(transactionDetails.getUsers().getId()));
            }

            return transactionRepository.save(transaction);
        }
        return null;
    }

    public boolean
    deleteTransaction(Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        }
        return false;
    }

}