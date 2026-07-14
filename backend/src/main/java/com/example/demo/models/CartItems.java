package com.example.demo.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@Entity
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartItemId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cartId",nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "foodItemId" , nullable = false)
    private FoodItems foodItems;

    @Column(name = "quantity")
    private Integer quantity;


}
