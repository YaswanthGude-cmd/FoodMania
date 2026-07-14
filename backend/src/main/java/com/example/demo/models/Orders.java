package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId" , nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "restaurantId" , nullable = false)
    private Restaurant restaurant;

    @Column(name = "totalPrice" , nullable = false)
    private Double totalPrice;

    @Column(name = "status" , nullable = false)
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<OrderItems> orderItems;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
