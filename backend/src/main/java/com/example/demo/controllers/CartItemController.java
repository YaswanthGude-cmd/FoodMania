package com.example.demo.controllers;


import com.example.demo.models.CartItems;
import com.example.demo.service.CartItemsService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cartitems")
public class CartItemController {

    private final CartItemsService cartItemsService;

    @Autowired
    public CartItemController(CartItemsService cartItemsService){
        this.cartItemsService = cartItemsService;
    }

    @GetMapping
    public List<CartItems> getAllCartItems(){
        return cartItemsService.getAllCartItems();
    }

    @PostMapping
    public ResponseEntity<HashMap<String , String>> createCartItem(@RequestBody CartItems cartItems){
        try{
            cartItemsService.createCartItem(cartItems);
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                 "status","1",
                 "message","cart item created successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }

    @JsonIgnore
    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String ,String>> updateCartItem(
            @PathVariable Long id,
            @RequestBody CartItems cartItems
    ){
        try{
            cartItemsService.updateCartItems(id, cartItems);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status","1",
                    "message","cart item is updated successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<HashMap<String , String >> clearCartItem(@PathVariable Long userId){
        try{
            cartItemsService.clearCartByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status","1",
                    "message","cart item deleted successfully"
            )));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }

    @GetMapping("/user/{userId}")
    public List<CartItems> getCartItemsByUserId(
            @PathVariable Long userId
    ){
        return cartItemsService.getCartItemsByUserId(userId);
    }

    @DeleteMapping("/{CartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long CartItemId){
        try{
            cartItemsService.deleteCartItem(CartItemId);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status","1",
                    "message","cart item deleted successfully"
            )));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }

}
