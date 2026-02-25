package com.example.FinanceTracker.Repository;

import com.example.FinanceTracker.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByNameAndTypeId(String name, int typeId);
}
