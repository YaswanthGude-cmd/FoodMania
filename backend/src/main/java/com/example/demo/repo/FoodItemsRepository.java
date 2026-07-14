package com.example.demo.repo;


import com.example.demo.dto.FoodItemResponse;
import com.example.demo.models.FoodItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodItemsRepository extends JpaRepository<FoodItems , Long> {

    public List<FoodItems> findByName(String name);

    List<FoodItems> findByCategory_Name(String categoryName);

    public void deleteByName(String name);

    public Boolean existsByName(String name);

//    public List<FoodItems> getFoodItemsByName(String name);

    Optional<FoodItems> findFoodItemsByName(String name) ;

    List<FoodItems> findFoodItemsById(Long id);


}
