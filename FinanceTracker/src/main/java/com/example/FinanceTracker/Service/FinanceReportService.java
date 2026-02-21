package com.example.FinanceTracker.Service;

import com.example.FinanceTracker.Model.Category;
import com.example.FinanceTracker.Repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
public class FinanceReportService {
    @Autowired
    private TransactionRepository transactionRepository;

    public MonthlySummary
    getMonthlySummary(Long userId, YearMonth yearMonth) {
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        double income = transactionRepository
                .sumAmountByUserAndTypeAndDateBetween(userId, Category.CategoryType.INCOME, start, end);
        double expense = transactionRepository
                .sumAmountByUserAndTypeAndDateBetween(userId, Category.CategoryType.EXPENSE, start, end);
        double balance = income - expense;

        return new MonthlySummary(yearMonth, income, expense, balance);
    }

    // Data Transfer Object (DTO)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MonthlySummary {
        private YearMonth month;
        private double totalIncome;
        private double totalExpense;
        private double netBalance;
    }
}