package com.ecommerce.Service;

import com.ecommerce.Exception.APIException;
import com.ecommerce.Exception.ResourceNotFoundException;
import com.ecommerce.Model.Category;
import com.ecommerce.Model.Product;
import com.ecommerce.Payload.ProductDTO;
import com.ecommerce.Payload.ProductResponse;
import com.ecommerce.Repository.CategoryRepository;
import com.ecommerce.Repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));

        product.setCategory(category);
        Double specialPrice = product.getPrice()-((product.getDiscount() * 0.01) * product.getPrice());

        product.setImage("default.png");
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        ///Sort and order starts here
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        ///For Pagination including the sort order up above
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> productPage = productRepository.findAll(pageDetails);
        List<Product> allPresent = productPage.getContent();
        ///pagination ends here

        if(allPresent.isEmpty())
            throw new APIException("The list is blank");
        List<ProductDTO> productDTOS = allPresent.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductContent(productDTOS);
        /// For pagination data in the get method
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());
        ///ends here pagination details

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));

        ///Sort and order starts here
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        ///For Pagination including the sort order up above
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        //Page<Product> productPage = productRepository.findAll(pageDetails);
        Page<Product> productPageWithCategory = productRepository.findByCategory(category,pageDetails);
        List<Product> allPresentWithCategory = productPageWithCategory.getContent();
        ///pagination ends here

        if(allPresentWithCategory.isEmpty())
            throw new APIException("The list is blank");
        List<ProductDTO> productDTOS = allPresentWithCategory.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductContent(productDTOS);
        /// For pagination data in the get method
        productResponse.setPageNumber(productPageWithCategory.getNumber());
        productResponse.setPageSize(productPageWithCategory.getSize());
        productResponse.setTotalElements(productPageWithCategory.getTotalElements());
        productResponse.setTotalPages(productPageWithCategory.getTotalPages());
        productResponse.setLastPage(productPageWithCategory.isLast());
        ///ends here pagination details

        return productResponse;
    }


}
