package com.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String description;
    private double discount;
    private String image;
    private double price;
    private String productName;
    private Integer quantity;
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_Id")
    private Category category;

    //private Long sellerId;

}