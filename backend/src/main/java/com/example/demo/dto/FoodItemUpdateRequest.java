package com.example.demo.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FoodItemUpdateRequest {

    private Long id;
    private String name;
    private String details;
    private Double price;
    private Boolean available;

    private Long restaurantId;
    private Long categoryId;

    private MultipartFile image;
}
