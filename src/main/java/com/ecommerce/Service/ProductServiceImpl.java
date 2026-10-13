package com.ecommerce.Service;

import com.ecommerce.Payload.ProductDTO;
import com.ecommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        return null;
    }
}
