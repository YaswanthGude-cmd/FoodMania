package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
@Entity

public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id ;

    @Column(name = "first_name" , nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_email" , nullable = false , unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "phoneNo" , nullable = false)
    private String phoneNo;

    @Column(name = "user_address")
    private String address;

    @OneToOne(mappedBy = "users" , cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Cart cart;

    @Column(name = "role" , nullable = false)
    private String role;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at" , nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();

        if (status == null) {
            status = "Active";
        }
    }

}

