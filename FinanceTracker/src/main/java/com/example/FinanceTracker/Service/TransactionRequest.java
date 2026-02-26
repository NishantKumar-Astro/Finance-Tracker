package com.example.FinanceTracker.Service;

import com.example.FinanceTracker.Model.CategoryType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionRequest {
    private String description;
    private Double amount;
    private LocalDate transactionDate;
    private String categoryName;
    private CategoryType categoryType;   // ← now enum
    private Long userId;
}
