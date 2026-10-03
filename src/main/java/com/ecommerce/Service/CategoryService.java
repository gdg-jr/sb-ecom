package com.ecommerce.Service;

import com.ecommerce.Model.Category;
import com.ecommerce.Payload.CategoryDTO;
import com.ecommerce.Payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    //List<Category> getAllCategories();
    CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);

    //void createCategory(Category category);
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    //String deleteCategory(Long categoryId);
    CategoryDTO deleteCategory(Long categoryId);

    //Category updateCategory(Category category, Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
