package com.example.FinanceTracker.Service;

import com.example.FinanceTracker.Model.CategoryType;
import com.example.FinanceTracker.Repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinanceReportServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private FinanceReportService reportService;

    @Test
    void testGetMonthlySummary() {
        Long userId = 1L;
        YearMonth yearMonth = YearMonth.of(2025, 3);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        when(transactionRepository.sumAmountByUserAndTypeAndDateBetween(userId, CategoryType.INCOME, start, end)).thenReturn(1000.0);
        when(transactionRepository.sumAmountByUserAndTypeAndDateBetween(userId, CategoryType.EXPENSE, start, end)).thenReturn(600.0);

        FinanceReportService.MonthlySummary summary = reportService.getMonthlySummary(userId, yearMonth);
        assertThat(summary.getMonth()).isEqualTo(yearMonth);
        assertThat(summary.getTotalIncome()).isEqualTo(1000.0);
        assertThat(summary.getTotalExpense()).isEqualTo(600.0);
        assertThat(summary.getNetBalance()).isEqualTo(400.0);
    }
}