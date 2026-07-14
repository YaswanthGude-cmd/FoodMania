package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemResponse {

    private Long id;
    private String name;
    private String details;
    private Double price;
    private Boolean available;

    private Long categoryId;
    private String categoryName;

    private Long restaurantId;
    private String restaurantName;

    private String image;
}
