package com.example.FinanceTracker.Controller;

import com.example.FinanceTracker.Model.Transaction;
import com.example.FinanceTracker.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService Service;

    @GetMapping
    public ResponseEntity<List<Transaction>>
    getAllTransactions() {
        return ResponseEntity.ok(Service.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction>
    getTransactionById(@PathVariable Long id) {
        Transaction transaction = Service.getTransactionById(id);
        return transaction != null ?
                ResponseEntity.ok(transaction) :
                ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Transaction>
    createTransaction(@RequestBody Transaction transaction) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Service.createTransaction(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction>
    updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        Transaction updatedTransaction =
                Service.updateTransaction(id, transaction);
        return updatedTransaction != null ?
                ResponseEntity.ok(updatedTransaction) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteTransaction(@PathVariable Long id) {
        return Service.deleteTransaction(id) ?
                ResponseEntity.ok("Transaction deleted successfully") :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Transaction not found");
    }
}