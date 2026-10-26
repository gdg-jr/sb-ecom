package com.ecommerce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="products")
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Size(min = 10, message = "Description should be more than 10!")
    private String description;
    private Double discount;
    private String image;
    private Double price;
    @NotBlank (message = "Product Name cannot be blank")
    @Size(min = 5, message = "Product Name should be more than 5!")
    private String productName;
    private Integer quantity;
    private Double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_Id")
    private Category category;

    //private Long sellerId; It is actually a link with user table who would sell.
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

}