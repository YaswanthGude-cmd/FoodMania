package com.example.demo.service;


import com.example.demo.models.Restaurant;
import com.example.demo.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private  RestaurantRepository restaurantRepository;

    public  RestaurantService(RestaurantRepository repo){
        this.restaurantRepository = repo;
    }

    public void createRestaurant(Restaurant restaurant){
        if(restaurantRepository.existsRestaurantByName(restaurant.getName())){
            throw new RuntimeException("Restaurant already exits");
        }

        restaurantRepository.save(restaurant);
    }

    public void updateRestaurant(Long id, Restaurant restaurant){

        Optional<Restaurant> tempRestaurant = restaurantRepository.findById(id);

        if(tempRestaurant.isEmpty()){
            throw new RuntimeException("Restaurant not found");
        }

        Restaurant currRestaurant = tempRestaurant.get();

        if(restaurant.getName() != null && !restaurant.getName().isEmpty()){
            currRestaurant.setName(restaurant.getName());
        }

        if(restaurant.getAddress() != null && !restaurant.getAddress().isEmpty()){
            currRestaurant.setAddress(restaurant.getAddress());
        }

        if(restaurant.getPhoneNo() != null && !restaurant.getPhoneNo().isEmpty()){
            currRestaurant.setPhoneNo(restaurant.getPhoneNo());
        }

        if(restaurant.getRating() != null){
            currRestaurant.setRating(restaurant.getRating());
        }

        restaurantRepository.save(currRestaurant);
    }

    public List<Restaurant> getAllRestaurants(){
        return restaurantRepository.findAll();
    }

    public List<Restaurant> getRestaurantByName(String name){
        return restaurantRepository.findRestaurantByName(name);
    }

    public void deleteRestaurant(Long id){
        if(id == null){
            throw new RuntimeException("invalid id");
        }
        if(!restaurantRepository.existsById(id)){
            throw new RuntimeException("Restaurant not found");
        }
        restaurantRepository.deleteById(id);
    }

}
