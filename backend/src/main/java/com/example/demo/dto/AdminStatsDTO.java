package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminStatsDTO {

    private Long totalUsers;
    private Long totalOrders;
    private Long totalFoods;
    private double revenue;
}
