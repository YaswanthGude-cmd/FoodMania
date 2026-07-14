package com.example.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FoodItemRequest {

    private String name;
    private String details;
    private double price;
    private long categoryId;
    private Long restaurantId;

    private MultipartFile image;
}
