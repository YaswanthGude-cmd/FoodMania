package com.example.demo.service;

import com.example.demo.dto.JwtResponseDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.models.Cart;
import com.example.demo.models.Users;
import com.example.demo.repo.CartRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, CartRepository cartRepository ,  JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
        this.jwtService = jwtService;
    }

    public JwtResponseDTO login(LoginRequestDTO request) {

        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new  RuntimeException("User not found"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Wrong password");
        }
        Optional<Cart> existingCart =
                cartRepository.findByUsers(user);

        if(existingCart.isEmpty()){

            Cart cart = new Cart();
            cart.setUsers(user);

            cartRepository.save(cart);
        }

        String token = jwtService.generateToken(user);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNo(),
                user.getAddress(),
                user.getRole()
        );

        JwtResponseDTO response = new JwtResponseDTO(
            token , loginResponseDTO
        );

        return response;
    }
}
