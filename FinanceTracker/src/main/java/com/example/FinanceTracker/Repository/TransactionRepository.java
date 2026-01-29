package com.example.FinanceTracker.Repository;

import com.example.FinanceTracker.Model.Category;
import com.example.FinanceTracker.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    // Core Reporting: Get transactions for a user in a date range
    List<Transaction> findByUser_IdAndDateBetween(Long userId, LocalDate start, LocalDate end);

    // Financial Summary: Get total income/expense for a user in a period
    @Query("SELECT COALESCE(SUM(t.amount), 0) " +
            "FROM Transaction t " +
            "JOIN t.category c " +
            "WHERE t.user.id = :userId " +
            "AND c.type = :type " +
            "AND t.date " +
            "BETWEEN :start " +
            "AND :end")
    Double sumAmountByUserAndTypeAndDateBetween(
            @Param("userId") Long userId,
            @Param("type") Category.CategoryType type,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);

    // Spending by Category: Useful for pie charts
    @Query("SELECT c.name, SUM(t.amount) " +
            "FROM Transaction t " +
            "JOIN t.category c " +
            "WHERE t.user.id = :userId " +
            "AND t.date BETWEEN :start " +
            "AND :end " +
            "AND c.type = 'EXPENSE' GROUP BY c.name")
    List<Object[]> findExpenseSummaryByCategory(
            @Param("userId") Long userId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);
}
