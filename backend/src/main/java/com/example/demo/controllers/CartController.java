package com.example.demo.controllers;


import com.example.demo.models.Cart;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService  cartService){
        this.cartService = cartService;
    }

    @GetMapping
    public List<Cart> getAllCarts(){
        return cartService.getAllCarts();
    }

    @PostMapping
    public ResponseEntity<HashMap<String , String>> createCart(@RequestBody Cart cart){
        try{
            cartService.createCart(cart);
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                    "status", "1",
                    "message","Cart created successfully"
            )));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status", "0",
                    "message",e.getMessage()
            )));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String , String>> updateCart(
            @PathVariable Long id,
            @RequestBody Cart cart
    ){
        try{
            cartService.updateCart(id , cart);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status","1",
                    "message","updated successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String , String>>deleteCart(
            @PathVariable Long id
    ){
        try{
            cartService.deleteCart(id);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status", "1",
                    "message","deleted successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCartByUserId(
            @PathVariable Long userId
    ){
        try{
            Cart cart = cartService.getCartByUserId(userId);
            if(cart == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Cart not found");
            }
            return ResponseEntity.ok(cart);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
