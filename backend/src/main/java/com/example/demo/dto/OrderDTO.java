package com.example.demo.dto;


import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {

    private Long orderId;

    private String customerName;
    private String phoneNo;
    private String address;

    private String restaurantName;

    private Double totalPrice;
    private String status;

    private List<OrderItemDTO> items;;
}
