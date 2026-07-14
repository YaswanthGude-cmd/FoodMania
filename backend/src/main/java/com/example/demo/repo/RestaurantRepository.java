package com.example.demo.repo;


import com.example.demo.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant , Long> {

    List<Restaurant> findRestaurantByName(String name);

    public void deleteById(Long id);

    public Boolean existsRestaurantByName(String name);

//    Optional<Restaurant> findByName(String name);

//    List<Restaurant> existsByName(String name);

//    List<Restaurant> getRestaurantById(Long id);

}
