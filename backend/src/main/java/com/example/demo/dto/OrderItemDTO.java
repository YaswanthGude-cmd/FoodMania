package com.example.demo.dto;


import lombok.Data;

@Data
public class OrderItemDTO {

    private Long foodItemId;
    private String foodName;
    private Integer quantity;
    private Double price;

}
