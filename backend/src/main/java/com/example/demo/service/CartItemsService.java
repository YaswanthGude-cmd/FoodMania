package com.example.demo.service;


import com.example.demo.models.Cart;
import com.example.demo.models.CartItems;
import com.example.demo.models.FoodItems;
import com.example.demo.repo.CartItemsRepository;
import com.example.demo.repo.CartRepository;
import com.example.demo.repo.FoodItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemsService {

    private final CartItemsRepository cartItemsRepository;
    private final CartRepository cartRepository;
    private final FoodItemsRepository foodItemsRepository;
    @Autowired
    public CartItemsService(
            CartItemsRepository cartItemsRepository ,
            CartRepository cartRepository,
            FoodItemsRepository foodItemsRepository
    ){
        this.cartItemsRepository = cartItemsRepository;
        this.cartRepository = cartRepository;
        this.foodItemsRepository = foodItemsRepository;
    }

    public void createCartItem(CartItems cartItems){

        if(cartItems.getCart() == null || cartItems.getFoodItems() == null){
            throw new RuntimeException("cart_id and foodItem_id required");
        }

        Cart cart = cartRepository.findById(
                cartItems.getCart().getId()
        ).orElseThrow(() ->
                new RuntimeException("cart not found")
        );

        FoodItems foodItem = foodItemsRepository.findById(
                cartItems.getFoodItems().getId()
        ).orElseThrow(() ->
                new RuntimeException("food item not found")
        );

        if(cartItems.getQuantity() <= 0){
            throw new RuntimeException("invalid quantity");
        }

        Optional<CartItems> existingCartItem =
                cartItemsRepository.findByCartAndFoodItems(
                        cart,
                        foodItem
                );

        if(existingCartItem.isPresent()){
            CartItems currCartItem = existingCartItem.get();
            currCartItem.setQuantity(
                    currCartItem.getQuantity() + cartItems.getQuantity()
            );
            cartItemsRepository.save(currCartItem);
        }else{
            cartItems.setCart(cart);
            cartItems.setFoodItems(foodItem);
            cartItemsRepository.save(cartItems);
        }
    }

    public void updateCartItems(Long id , CartItems cartItems){
        Optional<CartItems> tempCartItem = cartItemsRepository.findById(id);

        if(tempCartItem.isEmpty()){
            throw new RuntimeException("CartItem is not found");
        }else{
            CartItems currCartItem = tempCartItem.get();
            if(cartItems.getCart() !=  null ){
                Long cartId = cartItems.getCart().getId();
                if(cartId == null){
                    throw new RuntimeException("cart_id is required");
                }
                Cart cart = cartRepository.findById(cartId)
                        .orElseThrow(()-> new RuntimeException("cart is not found"));
                currCartItem.setCart(cart);
            }
            if(cartItems.getFoodItems() != null){
                Long foodItemsId = cartItems.getFoodItems().getId();
                Optional<FoodItems> foodItems = foodItemsRepository.findById(foodItemsId);
                if(foodItems.isEmpty()){
                    throw new RuntimeException("food item is not found");
                }
                FoodItems currFoodItems = foodItems.get();
                currCartItem.setFoodItems(currFoodItems);
            }

            if (cartItems.getQuantity() == null || cartItems.getQuantity() <= 0){
                throw new RuntimeException("invalid quantity");
            }
            currCartItem.setQuantity(cartItems.getQuantity());

            cartItemsRepository.save(currCartItem);

        }
    }

    public List<CartItems> getAllCartItems(){
        return cartItemsRepository.findAll();
    }

    @Transactional
    public void clearCartByUserId(Long userId) {
        try {
            List<CartItems> items = cartItemsRepository.findByCart_Users_Id(userId);
            if (items == null || items.isEmpty()) {
                return; // nothing to delete, not an error
            }
            cartItemsRepository.deleteByCart_Users_Id(userId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear cart: " + e.getMessage());
        }
    }

    public List<CartItems> getCartItemsByUserId(Long userId){

        return cartItemsRepository.findByCart_Users_Id(userId);
    }

    @Transactional
    public void deleteCartItem(Long CartItemId){
        cartItemsRepository.deleteById(CartItemId);
    }

}
