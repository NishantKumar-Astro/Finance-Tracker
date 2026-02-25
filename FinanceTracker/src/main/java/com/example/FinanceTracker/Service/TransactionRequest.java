package com.example.FinanceTracker.Service;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionRequest {
    private String description;
    private Double amount;
    private LocalDate transactionDate;
    private String categoryName;
    private int categoryType;   // 1 or 2
    private Long userId;
}
