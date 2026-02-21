package com.example.FinanceTracker.Repository;

import com.example.FinanceTracker.Model.Category;
import com.example.FinanceTracker.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUsers_IdAndTransactiondateBetween(Long userId, LocalDate start, LocalDate end);

    @Query("SELECT COALESCE(SUM(t.amount), 0) " +
            "FROM Transaction t " +
            "JOIN t.category c " +
            "WHERE t.users.id = :userId " +
            "AND c.type = :type " +
            "AND t.transactiondate " +
            "BETWEEN :start " +
            "AND :end")
    Double sumAmountByUserAndTypeAndDateBetween(
            @Param("userId") Long userId,
            @Param("type") Category.CategoryType type,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);

    @Query("SELECT c.name, SUM(t.amount) " +
            "FROM Transaction t " +
            "JOIN t.category c " +
            "WHERE t.users.id = :userId " +
            "AND t.transactiondate BETWEEN :start " +
            "AND :end " +
            "AND c.type = 'EXPENSE' GROUP BY c.name")
    List<Object[]> findExpenseSummaryByCategory(
            @Param("userId") Long userId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);

}
