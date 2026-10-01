package com.ecommerce.Controller;

import com.ecommerce.Service.CategoryService;
import com.ecommerce.Model.Category;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    public CategoryController(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }

//    @GetMapping("/api/public/categories")
//    public List<Category> getAllCategories(){
//        return categoryService.getAllCategories();
//    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categoryList = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryList, HttpStatus.FOUND);
    }

//    @PostMapping("/api/public/categories")
//    public String createCategory(@RequestBody Category category){
//        categoryService.createCategory(category);
//        String categoryName = category.getCategoryName();
//        Long categoryID = category.getCategoryId();
//        return "Add success! Category ID = " + categoryID+ " Category Name = "+ categoryName;
//    }

    @PostMapping("/api/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category);
        String categoryName = category.getCategoryName();
        Long categoryID = category.getCategoryId();
        return new ResponseEntity<>("Add success! Category ID = " + categoryID + " Category Name = " + categoryName, HttpStatus.ACCEPTED);
    }

//    @DeleteMapping("/api/admin/categories/{categoryId}")
//    public String deleteCategory(@PathVariable Long categoryId){
//        String status = categoryService.deleteCategory(categoryId);
//        return status;
//    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
            String status = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("/api/admin/editCategory/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long categoryId) {
        try {
            Category categorySaved = categoryService.updateCategory(category, categoryId);
            return new ResponseEntity<>("Success in Update", HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}
