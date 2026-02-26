package com.example.FinanceTracker.Repository;

import com.example.FinanceTracker.Model.Category;
import com.example.FinanceTracker.Model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameAndType(String name, CategoryType type);
}
