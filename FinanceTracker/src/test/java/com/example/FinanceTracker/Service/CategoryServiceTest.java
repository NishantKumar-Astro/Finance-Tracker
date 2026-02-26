package com.example.FinanceTracker.Service;

import com.example.FinanceTracker.Model.Category;
import com.example.FinanceTracker.Model.CategoryType;
import com.example.FinanceTracker.Repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("Food");
        testCategory.setType(CategoryType.EXPENSE); // expense
    }

    @Test
    void testGetAllCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(testCategory));
        List<Category> result = categoryService.getAllCategories();
        assertThat(result).hasSize(1).contains(testCategory);
    }

    @Test
    void testGetCategoryById_Found() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        Category result = categoryService.getCategoryById(1L);
        assertThat(result).isEqualTo(testCategory);
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());
        Category result = categoryService.getCategoryById(99L);
        assertThat(result).isNull();
    }

    @Test
    void testCreateCategory() {
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);
        Category input = new Category();
        input.setName("Food");
        input.setType(CategoryType.EXPENSE);
        Category saved = categoryService.createCategory(input);
        assertThat(saved).isEqualTo(testCategory);
    }

    @Test
    void testUpdateCategory_Success() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        Category details = new Category();
        details.setName("Groceries");
        details.setType(CategoryType.EXPENSE);
        Category updated = categoryService.updateCategory(1L, details);
        assertThat(updated).isNotNull();
        assertThat(updated.getName()).isEqualTo("Groceries");
    }

    @Test
    void testUpdateCategory_NotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());
        Category updated = categoryService.updateCategory(99L, testCategory);
        assertThat(updated).isNull();
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void testDeleteCategory_Success() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1L);
        boolean result = categoryService.deleteCategory(1L);
        assertThat(result).isTrue();
        verify(categoryRepository).deleteById(1L);
    }

    @Test
    void testDeleteCategory_NotFound() {
        when(categoryRepository.existsById(99L)).thenReturn(false);
        boolean result = categoryService.deleteCategory(99L);
        assertThat(result).isFalse();
        verify(categoryRepository, never()).deleteById(any());
    }

    @Test
    void testFindByNameAndType_Found() {
        when(categoryRepository.findByNameAndType("Food", CategoryType.EXPENSE)).thenReturn(Optional.of(testCategory));
        Category result = categoryService.findByNameAndType("Food", CategoryType.EXPENSE);
        assertThat(result).isEqualTo(testCategory);
    }

    @Test
    void testFindByNameAndType_NotFound() {
        when(categoryRepository.findByNameAndType("None", CategoryType.EXPENSE)).thenReturn(Optional.empty());
        Category result = categoryService.findByNameAndType("None", CategoryType.EXPENSE);
        assertThat(result).isNull();
    }
}