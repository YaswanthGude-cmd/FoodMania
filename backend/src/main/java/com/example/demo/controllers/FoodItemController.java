package com.example.demo.controllers;


import com.example.demo.dto.FoodItemRequest;
import com.example.demo.dto.FoodItemResponse;
import com.example.demo.dto.FoodItemUpdateRequest;
import com.example.demo.models.FoodItems;
import com.example.demo.service.FoodItemsService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fooditems")
public class FoodItemController {

    private final FoodItemsService foodItemsService;

    @Autowired
    public  FoodItemController(FoodItemsService foodItemsService){
        this.foodItemsService = foodItemsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FoodItemResponse>> getAllFoodItems(){
        return ResponseEntity.ok(foodItemsService.getAllFoodItems());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<FoodItemResponse>> getFoodItemsByCategory(@PathVariable String category){
        return ResponseEntity.ok(
                foodItemsService.getByCategory(category)
        );
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<FoodItemResponse> getFoodItemById(@PathVariable Long id){
        return ResponseEntity.ok(foodItemsService.getFoodItemById(id));
    }

    @PostMapping("/additem")
    public ResponseEntity<HashMap<String ,String>> createFoodItem(@ModelAttribute FoodItemRequest request){
        try{
            foodItemsService.createFoodItem(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                "status","1",
                "message","food item is created successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HashMap<String , String>> updateFoodItem(
            @PathVariable Long id,
            @ModelAttribute FoodItemUpdateRequest request){
        try{
            request.setId(id);
            foodItemsService.updateFoodItems(request);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status", "1",
                    "message","food item updated successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                    "status", "0",
                    "message",e.getMessage()
            )));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String , String>> deleteFoodItem(@PathVariable Long id){
        try{
            foodItemsService.deleteFoodItem(id);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status", "1",
                    "message", "Food item deleted successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName){
        try{
            Path imagePath = Paths.get("uploads").resolve(fileName);
            Resource resource = new UrlResource(imagePath.toUri());
            if(!resource.exists()){
                return ResponseEntity.notFound().build();
            }
            String contentType = Files.probeContentType(imagePath);
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

