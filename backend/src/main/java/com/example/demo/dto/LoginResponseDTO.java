package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String address;
    private String role;
}
