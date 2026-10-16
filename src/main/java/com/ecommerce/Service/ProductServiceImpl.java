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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;
    ///pulling the value set in the application properties
    @Value("${project.image}")
    private String path;

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

    @Override
    public ProductResponse getProductByKeyword(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyword) {
        ///Sort and order starts here
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        ///For Pagination including the sort order up above
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> productPageWithKeyword = productRepository.findByProductNameContainingIgnoreCase(keyword,pageDetails);
        List<Product> allPresentWithKeyword = productPageWithKeyword.getContent();
        ///pagination ends here

        if(allPresentWithKeyword.isEmpty())
            throw new APIException("The list is blank");
        List<ProductDTO> productDTOS = allPresentWithKeyword.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductContent(productDTOS);
        /// For pagination data in the get method
        productResponse.setPageNumber(productPageWithKeyword.getNumber());
        productResponse.setPageSize(productPageWithKeyword.getSize());
        productResponse.setTotalElements(productPageWithKeyword.getTotalElements());
        productResponse.setTotalPages(productPageWithKeyword.getTotalPages());
        productResponse.setLastPage(productPageWithKeyword.isLast());
        ///ends here pagination details

        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product product = modelMapper.map(productDTO,Product.class);
        Product productToSave = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","Product ID",productId));

        productToSave.setProductName((product.getProductName()));
        productToSave.setDescription((product.getDescription()));
        productToSave.setQuantity((product.getQuantity()));
        productToSave.setDiscount((product.getDiscount()));
        productToSave.setCategory((product.getCategory()));
        productToSave.setPrice((product.getPrice()));
        productToSave.setSpecialPrice(product.getPrice()-((product.getDiscount() * 0.01) * product.getPrice()));

        Product productSaved = productRepository.save(productToSave);
        return modelMapper.map(productSaved,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","Product ID",productId));
        productRepository.delete(savedProduct);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","Product ID",productId));

        ///Upload image in the server
        //String path = "images/";
        String fileName = fileService.uploadImage(path,image);
        productFromDB.setImage(fileName);
        Product updatedProduct = productRepository.save(productFromDB);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }




}
