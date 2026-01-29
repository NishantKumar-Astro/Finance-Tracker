package com.example.FinanceTracker.Service;

import com.example.FinanceTracker.Model.Category;
import com.example.FinanceTracker.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository Repo;

    public List<Category>
    getAllCategories() {
        return Repo.findAll();
    }

    public Category
    getCategoryById(Long id) {
        return Repo.findById(id).orElse(null);
    }

    public Category
    createCategory(Category category) {
        return Repo.save(category);
    }

    public Category
    updateCategory(Long id, Category categoryDetails) {
        Category category = getCategoryById(id);
        if (category != null) {
            category.setName(categoryDetails.getName());
            category.setType(categoryDetails.getType());
            return Repo.save(category);
        }
        return null;
    }

    public boolean
    deleteCategory(Long id) {
        if (Repo.existsById(id)) {
            Repo.deleteById(id);
            return true;
        }
        return false;
    }
}