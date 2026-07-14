package com.example.demo.service;


import com.example.demo.dto.FoodItemRequest;
import com.example.demo.dto.FoodItemResponse;
import com.example.demo.dto.FoodItemUpdateRequest;
import com.example.demo.models.Category;
import com.example.demo.models.FoodItems;
import com.example.demo.models.Restaurant;
import com.example.demo.repo.CategoryRepository;
import com.example.demo.repo.FoodItemsRepository;
import com.example.demo.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FoodItemsService {

    private final FoodItemsRepository foodItemsRepository;
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public FoodItemsService(FoodItemsRepository foodItemsRepository , RestaurantRepository restaurantRepository , CategoryRepository categoryRepository){
        this.foodItemsRepository = foodItemsRepository;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
    }

    public void createFoodItem(FoodItemRequest request) throws IOException{

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        FoodItems food = new FoodItems();

        food.setName(request.getName());
        food.setDetails(request.getDetails()); // IMPORTANT FIX
        food.setPrice(request.getPrice());
        if(request.getImage() != null && !request.getImage().isEmpty()) {
            MultipartFile imageFile = request.getImage();
            String fileName =System.currentTimeMillis()
                            + "_"
                            + imageFile.getOriginalFilename();
            Path uploadDir = Paths.get("uploads");
            if(!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            Files.copy(
                    imageFile.getInputStream(),
                    uploadDir.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING
            );
            food.setImageName(fileName);
        }
        food.setRestaurant(restaurant);
        food.setCategory(category);
        food.setAvailable(true);
        foodItemsRepository.save(food);
    }

    public void updateFoodItems(FoodItemUpdateRequest request) throws IOException {
        if(request.getId() == null){
            throw new RuntimeException("Food Item Id is required");
        }

        FoodItems currFoodItem = foodItemsRepository.findById(request.getId()).orElseThrow(()->new RuntimeException("Food Item  not found"));

        if(request.getName() != null && !request.getName().isEmpty()){
            currFoodItem.setName(request.getName());
        }
        if(request.getDetails() != null && !request.getDetails().isEmpty()){
            currFoodItem.setDetails(request.getDetails());
        }
        if(request.getAvailable() != null){
            currFoodItem.setAvailable(request.getAvailable());
        }
        if(request.getPrice()!= null){
            currFoodItem.setPrice(request.getPrice());
        }
        if(request.getRestaurantId() != null){
            Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow(()->new RuntimeException("Restaurant not found"));
            currFoodItem.setRestaurant(restaurant);
        }
        if(request.getCategoryId() != null){
            Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()->new RuntimeException("Category not found"));
            currFoodItem.setCategory(category);
        }
        if(request.getImage() != null && !request.getImage().isEmpty()){
            String oldImage = currFoodItem.getImageName();
            if(oldImage != null && !oldImage.isBlank() && !oldImage.startsWith("http")){
                Files.deleteIfExists(Paths.get("uploads").resolve(oldImage));
            }
            String fileName = System.currentTimeMillis() + "_" + request.getImage().getOriginalFilename();
            Path uploadDir = Paths.get("uploads");
            if(!Files.exists(uploadDir)){
                Files.createDirectories(uploadDir);
            }

            Files.copy(
                    request.getImage().getInputStream(),
                    uploadDir.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING
            );
            currFoodItem.setImageName(fileName);
        }
        foodItemsRepository.save(currFoodItem);
    }

    public List<FoodItemResponse> getByCategory(String category){
        return foodItemsRepository.findByCategory_Name(category).stream()
                .map(this::mapToResponse).toList();
    }

    public List<FoodItemResponse> getAllFoodItems(){
        return foodItemsRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public FoodItemResponse getFoodItemById(Long id){
        FoodItems food = foodItemsRepository.findById(id).orElseThrow(()->new RuntimeException("Food Item not found"));
        return mapToResponse(food);
    }

    public void deleteFoodItem(Long id){
        if(id == null){
            throw new RuntimeException("Invalid id");
        }
        if(!foodItemsRepository.existsById(id)){
            throw new RuntimeException("Food item not found");
        }
        foodItemsRepository.deleteById(id);
    }

    private FoodItemResponse mapToResponse(FoodItems food){
        String imageUrl;
        if(food.getImageName() != null && food.getImageName().startsWith("http")){
            imageUrl = food.getImageName();
        } else if ( food.getImageName() != null){
            imageUrl = "http://localhost:8080/api/fooditems/image/" + food.getImageName();
        }else{
            imageUrl = null;
        }
        return new FoodItemResponse(
                food.getId(),
                food.getName(),
                food.getDetails(),
                food.getPrice(),
                food.getAvailable(),
                food.getCategory().getId(),
                food.getCategory().getName(),
                food.getRestaurant().getId(),
                food.getRestaurant().getName(),
                imageUrl
        );
    }
}
