package com.example.demo.models;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@NoArgsConstructor
@Entity
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentId")
    private Long id;

    @OneToOne
    @JoinColumn(name = "orderId" , nullable = false)
    private Orders order;

    @Column(name = "amount" , nullable = false)
    private double amount;

    @Column(name = "paymentMethod" , nullable = false)
    private String paymentMethod;

    @Column(name = "paymentStatus" , nullable = false)
    private String paymentStatus;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateDate;
}
