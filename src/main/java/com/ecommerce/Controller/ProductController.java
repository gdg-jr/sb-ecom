package com.ecommerce.Controller;

import com.ecommerce.Config.AppConstants;
import com.ecommerce.Model.Product;
import com.ecommerce.Payload.ProductDTO;
import com.ecommerce.Payload.ProductResponse;
import com.ecommerce.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody Product product , @PathVariable Long categoryId) {
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

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(
            @RequestParam(name= "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name= "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name ="sortBy" ,defaultValue = AppConstants.SORT_PRODUCT_BY,required = false) String sortBy,
            @RequestParam(name ="sortOrder" ,defaultValue = AppConstants.SORT_PRODUCTS_ORDER,required = false) String sortOrder,
            @PathVariable Long categoryId
    ){
        ProductResponse productListByCategory = productService.getProductsByCategory(pageNumber,pageSize,sortBy,sortOrder,categoryId);
        return new ResponseEntity<>(productListByCategory,HttpStatus.FOUND);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(
            @RequestParam(name= "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name= "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name ="sortBy" ,defaultValue = AppConstants.SORT_PRODUCT_BY,required = false) String sortBy,
            @RequestParam(name ="sortOrder" ,defaultValue = AppConstants.SORT_PRODUCTS_ORDER,required = false) String sortOrder,
            @PathVariable String keyword
    ){
        ProductResponse productListByKeyword = productService.getProductByKeyword(pageNumber,pageSize,sortBy,sortOrder,keyword);
        return new ResponseEntity<>(productListByKeyword,HttpStatus.FOUND);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long productId){
        ProductDTO productDTOtoUpdate = productService.updateProduct(productDTO,productId);
        return new ResponseEntity<>(productDTOtoUpdate,HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO productDTOtoDelete = productService.deleteProduct(productId);
        return new ResponseEntity<>(productDTOtoDelete,HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId, @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO productDTOtoUpdateImage = productService.updateProductImage(productId,image);
        return new ResponseEntity<>(productDTOtoUpdateImage,HttpStatus.OK);
    }
}
