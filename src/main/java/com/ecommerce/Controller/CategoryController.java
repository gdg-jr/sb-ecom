package com.ecommerce.Controller;

import com.ecommerce.Config.AppConstants;
import com.ecommerce.Payload.CategoryDTO;
import com.ecommerce.Payload.CategoryResponse;
import com.ecommerce.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;



@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

/// This is for learning pagination. The mapping is not
    @GetMapping("/api/echo")
    public ResponseEntity<String> echoMessage(@RequestParam(name = "message" , defaultValue = "Hello world", required = false) String message) {
        return new ResponseEntity<>("Message: =" + message , HttpStatus.OK);
    }
    /// required anywhere else. Not deleting it.

//    public CategoryController(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }

//    @GetMapping("/api/public/categories")
//    public List<Category> getAllCategories(){
//        return categoryService.getAllCategories();
//    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name= "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name= "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name ="sortBy" ,defaultValue = AppConstants.SORT_CATEGORIES_BY,required = false) String sortBy,
            @RequestParam(name ="sortOrder" ,defaultValue = AppConstants.SORT_CATEGORIES_ORDER,required = false) String sortOrder
    ) {
        CategoryResponse categoryList = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
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
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        String categoryName = savedCategoryDTO.getCategoryName();
        Long categoryID = savedCategoryDTO.getCategoryId();
        return new ResponseEntity<>("Created " + categoryName + " with id " + categoryID, HttpStatus.ACCEPTED);
    }

//    @DeleteMapping("/api/admin/categories/{categoryId}")
//    public String deleteCategory(@PathVariable Long categoryId){
//        String status = categoryService.deleteCategory(categoryId);
//        return status;
//    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        CategoryDTO statusDTO = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(statusDTO, HttpStatus.OK);
    }

    @PutMapping("/api/admin/editCategory/{categoryId}")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {
        try {
            CategoryDTO categorySavedDTO = categoryService.updateCategory(categoryDTO, categoryId);
            return new ResponseEntity<>("Success in Update", HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}
