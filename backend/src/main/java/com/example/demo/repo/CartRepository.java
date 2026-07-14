package com.example.demo.repo;


import com.example.demo.models.Cart;
import com.example.demo.models.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart , Long> {

     Optional<Cart> findById(Long id);

     void deleteById(Long id);

     Optional<Cart> findByUsers_Id(Long userId);

     Optional<Cart> findByUsers(Users users);

//    Long id(Long id);
}
