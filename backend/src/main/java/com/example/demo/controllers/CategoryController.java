package com.example.demo.controllers;

import com.example.demo.models.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping
    public ResponseEntity<HashMap<String , String>> createCategory( @RequestBody Category category){
        try{
            categoryService.createCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                    "status","1",
                    "message", "Created successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String , String >> updateCategory(
            @PathVariable Long id ,
            @RequestBody Category  category
    ){

        try {
            categoryService.updateCategory(id , category);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status","1",
                    "message","updated successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String , String >> deleteCategory(@PathVariable Long id){
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status","1",
                    "message", "Deleted successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status", "0",
                    "message",e.getMessage()
            )));
        }
    }
}
