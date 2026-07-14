package com.example.demo.service;


import com.example.demo.dto.AdminStatsDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.models.Cart;
import com.example.demo.models.Users;
import com.example.demo.repo.CartRepository;
import com.example.demo.repo.FoodItemsRepository;
import com.example.demo.repo.OrdersRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrdersRepository  ordersRepository;
    private final FoodItemsRepository foodItemsRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UserRepository repo , CartRepository cartRepository , OrdersRepository ordersRepository , FoodItemsRepository foodItemsRepository ,  PasswordEncoder passwordEncoder) {
        this.userRepository = repo;
        this.cartRepository = cartRepository;
        this.ordersRepository = ordersRepository;
        this.foodItemsRepository = foodItemsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(Users users){

        if(userRepository.existsByEmail(users.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        // FORCE ROLE HERE (MOST IMPORTANT FIX)
        users.setRole("USER");
        users.setStatus("ACTIVE");
        // For encoding the password
        String encodedPassword = passwordEncoder.encode(users.getPassword());
        users.setPassword(encodedPassword);
        // Save user first
        Users savedUser = userRepository.save(users);
        // Create cart automatically
        Cart cart = new Cart();
        cart.setUsers(savedUser);
        cartRepository.save(cart);
    }

    public Users updateUser(Users users) {
        // 1. Validate ID
        if (users.getId() == null) {
            throw new RuntimeException("User ID is required");
        }
        // 2. Find existing user
        Optional<Users> optionalUser =
                userRepository.findById(users.getId());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException(
                    "User with ID " + users.getId() + " not found"
            );
        }
        Users currUser = optionalUser.get();
        // 3. Update fields safely
        if (users.getFirstName() != null &&
                !users.getFirstName().trim().isEmpty()) {
            currUser.setFirstName(users.getFirstName().trim());
        }
        if (users.getLastName() != null &&
                !users.getLastName().trim().isEmpty()) {
            currUser.setLastName(users.getLastName().trim());
        }
        if (users.getEmail() != null &&
                !users.getEmail().trim().isEmpty()) {
            currUser.setEmail(users.getEmail().trim());
        }
        if (users.getPassword() != null &&
                !users.getPassword().trim().isEmpty()) {
            currUser.setPassword(users.getPassword().trim());
        }
        if (users.getPhoneNo() != null &&
                !users.getPhoneNo().trim().isEmpty()) {
            currUser.setPhoneNo(users.getPhoneNo().trim());
        }
        if (users.getAddress() != null &&
                !users.getAddress().trim().isEmpty()) {

            currUser.setAddress(users.getAddress().trim());
        }
        if(users.getStatus() != null && !users.getStatus().trim().isEmpty()){
            currUser.setStatus(users.getStatus().trim());
        }
        // 4. Save updated user
        return userRepository.save(currUser);
    }

/*
 public List<Users> getUsersById(Long id){
     return userRepository.findUsersById(id);
 }
 public List<Users> getUsersProfile(String Email){
     return userRepository.findUsersByEmail(Email);
 }
*/

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

//    public List<Users> getAllUsersByRole(String role){
//        return userRepository.findAllByRole(role);
//    }

    public void deleteUserById(Users users){
        if(users.getEmail() == null){
            throw new RuntimeException("please enter the Email to delete the User");
        }else {
            if(!users.getEmail().isEmpty()){
                List<Users> currUser = userRepository.findUsersByEmail(users.getEmail());

                if(currUser.isEmpty()){
                    throw new RuntimeException("No User is found");
                }
                else{
                    userRepository.deleteUsersByEmail(users.getEmail());
                }
            }
        }
    }

    public List<UserResponseDTO> getUsersForAdmin(){
        return userRepository.getUsersForAdmin();
    }

    public Users updateUserStatus(Long  id,String status){
        Users user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(status);
        return userRepository.save(user);
    }

    public AdminStatsDTO getStats(){
        long users = userRepository.count();
        long orders = ordersRepository.count();
        long foods = foodItemsRepository.count();
        double revenue = ordersRepository.getRevenue();

        return new AdminStatsDTO(users , orders , foods, revenue);
    }

}
