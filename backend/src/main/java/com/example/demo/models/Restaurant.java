package com.example.demo.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurantId")
    private Long id;

    @Column(name = "restaurantName", nullable = false)
    private String name ;

    @Column(name = "location" , nullable = false)
    private String address;

    @Column(name = "phoneNo")
    private String phoneNo;

    @Column(name = "restaurantRating")
    private Double rating;

}
