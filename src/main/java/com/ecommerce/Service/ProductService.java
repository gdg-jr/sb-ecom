package com.ecommerce.Service;

import com.ecommerce.Model.Product;
import com.ecommerce.Payload.ProductDTO;
import com.ecommerce.Payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Product product, Long categoryId);

    ProductResponse getAllProducts(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
}
