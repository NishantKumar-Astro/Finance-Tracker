package com.example.FinanceTracker.Controller;

import com.example.FinanceTracker.Model.Transaction;
import com.example.FinanceTracker.Service.TransactionRequest;
import com.example.FinanceTracker.Service.TransactionService;
import jakarta.validation.Valid;
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

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Transaction>>
    getTransactionByUser_id(@PathVariable Long id){
        try{
            List<Transaction> transactions = Service.getTransactionByUserid(id);
            if (transactions != null)
                return ResponseEntity.ok(transactions);
            else return ResponseEntity.notFound().build();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Transaction>
    createTransaction(@Valid @RequestBody TransactionRequest transaction) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Service.createTransaction(transaction));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long id,
            @RequestBody TransactionRequest request) {
        Transaction updatedTransaction = Service.updateTransaction(id, request);
        return updatedTransaction != null ?
                ResponseEntity.ok(updatedTransaction) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String>
    deleteTransaction(@PathVariable Long id) {
        return Service.deleteTransaction(id) ?
                ResponseEntity.ok("Transaction deleted successfully") :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Transaction not found");
    }
}