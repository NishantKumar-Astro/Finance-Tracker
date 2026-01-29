package com.example.FinanceTracker.Repository;

import com.example.FinanceTracker.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
