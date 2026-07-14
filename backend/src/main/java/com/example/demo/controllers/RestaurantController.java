package com.example.demo.controllers;


import com.example.demo.models.Restaurant;
import com.example.demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @GetMapping("/all")
    public List<Restaurant> getAllRestaurants(){
        return restaurantService.getAllRestaurants();
    }

    @PostMapping
    public ResponseEntity<HashMap<String , String>> createRestaurant(@RequestBody Restaurant restaurant){
        try{
            restaurantService.createRestaurant(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                    "status","1",
                    "message", "Restaurant has updated"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status", "0",
                    "message",e.getMessage()
            )));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String,String>> updateRestaurant(
            @PathVariable Long id,
            @RequestBody Restaurant restaurant)
    {
        restaurantService.updateRestaurant(id, restaurant);
        try{
            return ResponseEntity.ok(
                    new HashMap<>(Map.of(
                            "status","1",
                            "message","Restaurant updated"
                    )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CREATED).body((new HashMap<>(Map.of(
                    "status", "0",
                    "message", e.getMessage()
            ))));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String , String>> deleteRestaurant(@PathVariable Long id){
        try{
            restaurantService.deleteRestaurant(id);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status","1",
                    "message","Restaurant deleted successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((new HashMap<>(Map.of(
                    "status", "0",
                    "message",e.getMessage()
            ))));
        }
    }

}
