package com.example.demo.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity


public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "foodItemId" , nullable = false)
    private FoodItems foodItems;

    @Column(name = "quantity" , nullable = false)
    private int quantity;

    @Column(name = "price" , nullable = false)
    private double price;

}
