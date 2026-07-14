package com.example.demo.service;


import com.example.demo.models.Cart;
import com.example.demo.models.Users;
import com.example.demo.repo.CartRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    @Autowired
    public CartService(CartRepository cartRepository , UserRepository userRepository){
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public void createCart(Cart cart){
        if(cart.getUsers() == null){
            throw new RuntimeException("user is required");
        }
        cartRepository.save(cart);
    }

    public void updateCart(Long Id ,Cart cart){
        Optional<Cart> tempCart = cartRepository.findById(Id);

        if(tempCart.isEmpty()){
            throw new RuntimeException("Cart not found");
        }else {
            Cart currCart = tempCart.get();
            if(cart.getUsers() != null){
                Long userId = cart.getUsers().getId();
                Users users  = userRepository.findById(userId).
                        orElseThrow(() -> new RuntimeException("user is not found"));
                currCart.setUsers(users);
            }
        }
    }

    public List<Cart> getAllCarts(){
        return cartRepository.findAll();
    }

    public void deleteCart(Long id ){
        if(id == null){
            throw new RuntimeException("Invalid id");
        }
        if(!cartRepository.existsById(id)){
            throw new RuntimeException("cart is not found");
        }
        cartRepository.deleteById(id);
    }

    public Cart getCartByUserId(Long userId){

        return cartRepository.findByUsers_Id(userId)
                .orElseThrow(() ->
                        new RuntimeException("Cart not found")
                );
    }
}
