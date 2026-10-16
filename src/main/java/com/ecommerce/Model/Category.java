package com.ecommerce.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity (name = "Categories")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank(message ="Category cannot be Blank!")
    @Size(min = 5, message = "Catogery size is outside limit!")
    private String categoryName;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> product =new ArrayList<>();


//    public Category(Long categoryId, String categoryName) {
//        this.categoryId = categoryId;
//        this.categoryName = categoryName;
//    }
//
//    public Category() {
//    }
//
//    public Long getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Long categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    public String getCategoryName() {
//        return categoryName;
//    }
//
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }
}
