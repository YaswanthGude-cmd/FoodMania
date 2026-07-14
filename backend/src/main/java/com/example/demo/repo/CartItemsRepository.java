package com.example.demo.repo;


import com.example.demo.models.Cart;
import com.example.demo.models.CartItems;
import com.example.demo.models.FoodItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems , Long> {

    Optional<CartItems> findById(Long id);

    Optional<CartItems> findByCartAndFoodItems(Cart cart, FoodItems foodItems);

    List<CartItems> findByCart_Users_Id(Long userId);

//    public Boolean existsById(Long id);

    void deleteByCart_Users_Id(Long userId);

}
