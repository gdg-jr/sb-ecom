package com.ecommerce.Service;

import com.ecommerce.Exception.APIException;
import com.ecommerce.Exception.ResourceNotFoundException;
import com.ecommerce.Model.Category;
import com.ecommerce.Payload.CategoryDTO;
import com.ecommerce.Payload.CategoryResponse;
import com.ecommerce.Repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    //private List<Category> categories = new ArrayList<>();
    //private Long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    //@Override
//    public List<Category> getAllCategories() {
//        //return categories;
//        List<Category> allPresent = categoryRepository.findAll();
//        if(allPresent.isEmpty())
//            throw new APIException("The list is blank");
//        return allPresent;
//    }

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> allPresent = categoryRepository.findAll();
        if(allPresent.isEmpty())
            throw new APIException("The list is blank");
        List<CategoryDTO> categoryDTOS = allPresent.stream()
                .map(category -> modelMapper.map(category,CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryContent(categoryDTOS);
        return categoryResponse;
    }

//    @Override
//    public void createCategory(Category category) {
//        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
//        if (savedCategory!=null)
//            throw new APIException("Category with the name " + category.getCategoryName()+ " already exists! ");
//        categoryRepository.save(category);
//    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO,Category.class);
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory!=null)
            throw new APIException("Category with the name " + category.getCategoryName()+ " already exists! ");
        Category categorySaved = categoryRepository.save(category);
        return modelMapper.map(categorySaved,CategoryDTO.class);
    }

//    @Override
//    public String deleteCategory(Long categoryId) {
//        Category category = (Category) categories.stream()
//                .filter(c ->c.getCategoryId().equals(categoryId))
//                .findFirst().orElse(null);
//
//        if (category == null) {
//            return "Category with ID " + categoryId+ " not found !" ;
//        }
//        else {
//            String categoryName = category.getCategoryName();
//            Long categoryIdTaken = category.getCategoryId();
//            categories.remove(category);
//            return "Category with category ID" + categoryIdTaken + " deleted successfully! Name was : " + categoryName;
//        }
//    }

//    @Override
//    public String deleteCategory(Long categoryId) {
//        Category category = (Category) categories.stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst()
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category ID " + categoryId + " not found"));
//
//        String categoryName = category.getCategoryName();
//        Long categoryIdTaken = category.getCategoryId();
//        categories.remove(category);
//        return "Category with category ID " + categoryIdTaken + " deleted successfully! Name was : " + categoryName;
//
//    }

//    @Override
//    public String deleteCategory(Long categoryId) {
//        Category savedCategory = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category ID not found. Nothing to Delete!!"));
//
//        String categoryName = savedCategory.getCategoryName();
//        Long categoryIdTaken = savedCategory.getCategoryId();
//        categoryRepository.delete(savedCategory);
//        return "Category with category ID " + categoryIdTaken + " deleted successfully! Name was : " + categoryName;
//
//    }

//    @Override
//    public String deleteCategory(Long categoryId) {
//        Category savedCategory = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new ResourceNotFoundException("Category","Category ID",categoryId));
//
//        String categoryName = savedCategory.getCategoryName();
//        Long categoryIdTaken = savedCategory.getCategoryId();
//        categoryRepository.delete(savedCategory);
//        return "Category with category ID " + categoryIdTaken + " deleted successfully! Name was : " + categoryName;
//
//    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {

        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Category ID",categoryId));
        categoryRepository.delete(savedCategory);
        return modelMapper.map(savedCategory,CategoryDTO.class);

    }

//    @Override
//    public Category updateCategory(Category category, Long categoryId) {
//        Optional<Category> categoryForUpdate = categories.stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst();
//
//        if (categoryForUpdate.isPresent()) {
//            Category existingCategory = categoryForUpdate.get();
//            existingCategory.setCategoryName(category.getCategoryName());
//            return existingCategory;
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category ID not found");
//        }
//    }

//    @Override
//    public Category updateCategory(Category category, Long categoryId) {
//
//        Category savedCategory = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new ResourceNotFoundException("Category","Category ID",categoryId));
//
//        category.setCategoryId(categoryId);
//       savedCategory = categoryRepository.save(category);
//       return  savedCategory;
//    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category = modelMapper.map(categoryDTO,Category.class);
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Category ID",categoryId));
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }
}
