package com.example.FinanceTracker.Controller;

import com.example.FinanceTracker.Model.Category;
import com.example.FinanceTracker.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService Service;

    @GetMapping
    public ResponseEntity<List<Category>>
    getAllCategories() {
        return ResponseEntity.ok(Service.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category>
    getCategoryById(@PathVariable Long id) {
        Category category = Service.getCategoryById(id);
        return category != null ?
                ResponseEntity.ok(category) :
                ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Category>
    createCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Service.createCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category>
    updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category updatedCategory = Service.updateCategory(id, category);
        return updatedCategory != null ?
                ResponseEntity.ok(updatedCategory) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteCategory(@PathVariable Long id) {
        return Service.deleteCategory(id) ?
                ResponseEntity.ok("Category deleted successfully") :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Category not found");
    }
}