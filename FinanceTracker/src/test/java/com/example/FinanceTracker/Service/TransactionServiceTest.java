package com.example.FinanceTracker.Service;

import com.example.FinanceTracker.Model.Category;
import com.example.FinanceTracker.Model.CategoryType;
import com.example.FinanceTracker.Model.Transaction;
import com.example.FinanceTracker.Model.Users;
import com.example.FinanceTracker.Repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TransactionService transactionService;

    private Category testCategory;
    private Users testUser;
    private Transaction testTransaction;
    private TransactionRequest request;

    @BeforeEach
    void setUp() {
        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("Food");
        testCategory.setType(CategoryType.EXPENSE);

        testUser = new Users();
        testUser.setId(1L);
        testUser.setUsername("john");

        testTransaction = new Transaction();
        testTransaction.setId(1L);
        testTransaction.setDescription("Lunch");
        testTransaction.setAmount(25.50);
        testTransaction.setTransactiondate(LocalDate.of(2025, 3, 1));
        testTransaction.setCategory(testCategory);
        testTransaction.setUsers(testUser);

        request = new TransactionRequest();
        request.setDescription("Lunch");
        request.setAmount(25.50);
        request.setTransactionDate(LocalDate.of(2025, 3, 1));
        request.setCategoryName("Food");
        request.setCategoryType(CategoryType.EXPENSE);
        request.setUserId(1L);
    }

    @Test
    void testGetAllTransactions() {
        when(transactionRepository.findAll()).thenReturn(List.of(testTransaction));
        List<Transaction> result = transactionService.getAllTransactions();
        assertThat(result).hasSize(1).contains(testTransaction);
    }

    @Test
    void testGetTransactionById_Found() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(testTransaction));
        Transaction result = transactionService.getTransactionById(1L);
        assertThat(result).isEqualTo(testTransaction);
    }

    @Test
    void testGetTransactionById_NotFound() {
        when(transactionRepository.findById(99L)).thenReturn(Optional.empty());
        Transaction result = transactionService.getTransactionById(99L);
        assertThat(result).isNull();
    }

    @Test
    void testCreateTransaction_Success() {
        when(categoryService.findByNameAndType("Food", CategoryType.EXPENSE)).thenReturn(testCategory);
        when(userService.getUserById(1L)).thenReturn(testUser);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(testTransaction);

        Transaction result = transactionService.createTransaction(request);
        assertThat(result).isEqualTo(testTransaction);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void testCreateTransaction_CategoryNotFound() {
        when(categoryService.findByNameAndType("Food", CategoryType.EXPENSE)).thenReturn(null);
        assertThatThrownBy(() -> transactionService.createTransaction(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Category not found");
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void testCreateTransaction_UserNotFound() {
        when(categoryService.findByNameAndType("Food", CategoryType.EXPENSE)).thenReturn(testCategory);
        when(userService.getUserById(1L)).thenReturn(null);
        assertThatThrownBy(() -> transactionService.createTransaction(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void testUpdateTransaction_Success() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(testTransaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(testTransaction);

        TransactionRequest updateRequest = new TransactionRequest();
        updateRequest.setDescription("Updated Lunch");
        updateRequest.setAmount(30.00);
        updateRequest.setTransactionDate(LocalDate.of(2025, 3, 2));
        // optionally update category
        updateRequest.setCategoryName("Food");
        updateRequest.setCategoryType(CategoryType.EXPENSE);
        updateRequest.setUserId(1L);

        when(categoryService.findByNameAndType("Food", CategoryType.EXPENSE)).thenReturn(testCategory);
        when(userService.getUserById(1L)).thenReturn(testUser);

        Transaction updated = transactionService.updateTransaction(1L, updateRequest);
        assertThat(updated).isNotNull();
        assertThat(updated.getDescription()).isEqualTo("Updated Lunch");
        assertThat(updated.getAmount()).isEqualTo(30.00);
        assertThat(updated.getTransactiondate()).isEqualTo(LocalDate.of(2025, 3, 2));
    }

    @Test
    void testUpdateTransaction_NotFound() {
        when(transactionRepository.findById(99L)).thenReturn(Optional.empty());
        Transaction updated = transactionService.updateTransaction(99L, request);
        assertThat(updated).isNull();
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void testDeleteTransaction_Success() {
        when(transactionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(transactionRepository).deleteById(1L);
        boolean result = transactionService.deleteTransaction(1L);
        assertThat(result).isTrue();
        verify(transactionRepository).deleteById(1L);
    }

    @Test
    void testDeleteTransaction_NotFound() {
        when(transactionRepository.existsById(99L)).thenReturn(false);
        boolean result = transactionService.deleteTransaction(99L);
        assertThat(result).isFalse();
        verify(transactionRepository, never()).deleteById(any());
    }

    @Test
    void testGetTransactionByUserId() {
        when(transactionRepository.findByUsersId(1L)).thenReturn(List.of(testTransaction));
        List<Transaction> result = transactionService.getTransactionByUserid(1L);
        assertThat(result).hasSize(1).contains(testTransaction);
    }
}