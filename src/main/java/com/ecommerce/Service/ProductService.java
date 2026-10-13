package com.ecommerce.Service;

import com.ecommerce.Payload.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);
}
