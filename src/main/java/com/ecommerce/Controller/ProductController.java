package com.ecommerce.Controller;

import com.ecommerce.Config.AppConstants;
import com.ecommerce.Model.Product;
import com.ecommerce.Payload.ProductDTO;
import com.ecommerce.Payload.ProductResponse;
import com.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product , @PathVariable Long categoryId) {
        ProductDTO addProduct = productService.addProduct(product,categoryId);
        return new ResponseEntity<>(addProduct, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name= "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name= "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name ="sortBy" ,defaultValue = AppConstants.SORT_PRODUCT_BY,required = false) String sortBy,
            @RequestParam(name ="sortOrder" ,defaultValue = AppConstants.SORT_PRODUCTS_ORDER,required = false) String sortOrder
    ){
        ProductResponse productList = productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productList,HttpStatus.FOUND);
    }
}
