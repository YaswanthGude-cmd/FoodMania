package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class UserResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String address;
    private String role;
    private String status;
    private Long totalOrders;
    private LocalDateTime joined;
}
