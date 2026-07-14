package com.example.demo.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class FoodItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "foodItemId")
    private Long id;

    @Column(name = "itemName" , nullable = false)
    private String name;

    @Column(name = "description" )
    private  String details;

    @Column(name = "itemPrice" , nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id" , nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "category_id" , nullable = false)
    private Category category;

    @Column(name = "stockStatus")
    private Boolean available;

    @Column(name = "imageName")
    private String imageName;

}
