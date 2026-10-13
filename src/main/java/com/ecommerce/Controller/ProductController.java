package com.ecommerce.Controller;

import com.ecommerce.Payload.ProductDTO;
import com.ecommerce.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/api/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO ,@PathVariable Long categoryId) {
        ProductDTO addProduct = productService.addProduct(productDTO,categoryId);
        return new ResponseEntity<>(addProduct, HttpStatus.OK);
    }
}
