package com.example.demo.service;


import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.models.*;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final CartItemsRepository cartItemsRepository;
    private final CartItemsService cartItemsService;

    @Autowired
    public OrdersService(
            OrdersRepository ordersRepository, UserRepository userRepository, RestaurantRepository restaurantRepository , OrderItemsRepository orderItemsRepository,
            CartItemsRepository cartItemsRepository ,  CartItemsService cartItemsService
    ) {
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.cartItemsService = cartItemsService;
    }

    public void createOrder(Orders orders){
        if(orders.getUser()==null || orders.getRestaurant()==null){
            throw new RuntimeException("User id and restaurant id can't be null");
        }
        Users user = userRepository.findById(orders.getUser().getId())
                .orElseThrow(()-> new RuntimeException("user not found"));

        Restaurant restaurant = restaurantRepository.findById(orders.getRestaurant().getId())
                .orElseThrow(()-> new RuntimeException("restaurant not found"));
        if(orders.getTotalPrice() < 0)
            throw new RuntimeException("total price can't be negative");
        orders.setUser(user);
        orders.setRestaurant(restaurant);
        ordersRepository.save(orders);
    }

    public void updateOrder(Long id ,  Orders orders){
        Optional<Orders> tempOrders = ordersRepository.findById(id);
        if(tempOrders.isEmpty()){
            throw new RuntimeException("order not found");
        }
        else{
            Orders currOrder = tempOrders.get();
            if(orders.getUser() !=  null){
                Long userId = orders.getUser().getId();
                Users user = userRepository.findById(userId)
                        .orElseThrow(()-> new RuntimeException("user not found"));
                currOrder.setUser(user);
            }
            if(orders.getRestaurant() != null){
                Long restaurantId = orders.getRestaurant().getId();
                Restaurant restaurant = restaurantRepository.findById(restaurantId)
                        .orElseThrow(()-> new RuntimeException("restaurant not found"));
                currOrder.setRestaurant(restaurant);
            }
            if(orders.getTotalPrice() < 0){
                throw new RuntimeException("total price can't be negative");
            }else{
                currOrder.setTotalPrice(orders.getTotalPrice());
            }
            if(orders.getStatus() != null && !orders.getStatus().isEmpty()){
                currOrder.setStatus(orders.getStatus());
            }
            ordersRepository.save(currOrder);
        }
    }

    public List<Orders> getAllOrders(){
        return ordersRepository.findAll();
    }

    public void deleteOrder(Long id){
        if(id == null){
            throw new RuntimeException("id can't be null");
        }
        if(!ordersRepository.existsById(id)){
            throw new RuntimeException("order not found");
        }
        ordersRepository.deleteById(id);
    }

    @Transactional
    public void placeOrder(Long userId){

        Users user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("user not found"));
        if("Blocked".equalsIgnoreCase(user.getStatus())){
            throw new RuntimeException("Your account has been blocked. Contact support for assistance.");
        }
        List<CartItems> cartItems = cartItemsRepository.findByCart_Users_Id(userId);

        if(cartItems == null || cartItems.isEmpty()){
            throw new RuntimeException("cart is empty");
        }

//        calculating totalPrice
        double totalPrice = 0;
        for(CartItems item : cartItems){
            totalPrice += item.getFoodItems().getPrice()*item.getQuantity();
        }

//        create order
        Orders orders = new Orders();
        orders.setUser(cartItems.getFirst().getCart().getUsers());
        orders.setRestaurant(cartItems.getFirst().getFoodItems().getRestaurant());
        orders.setTotalPrice(totalPrice);
        orders.setStatus("P");
        Orders saveOrder = ordersRepository.save(orders);

//        create orderItems
        for(CartItems item : cartItems){
            OrderItems orderItems = new OrderItems();
            orderItems.setOrder(saveOrder);
            orderItems.setFoodItems(item.getFoodItems());
            orderItems.setQuantity(item.getQuantity());
            orderItems.setPrice(item.getFoodItems().getPrice()*item.getQuantity());
            orderItemsRepository.save(orderItems);
        }

        cartItemsService.clearCartByUserId(userId);
    }

    public OrderDTO convertToDTO(Orders order){
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getId());
        dto.setCustomerName(order.getUser().getFirstName() + " " + order.getUser().getLastName());
        dto.setPhoneNo(order.getUser().getPhoneNo());
        dto.setAddress(order.getUser().getAddress());
        dto.setRestaurantName(order.getRestaurant().getName());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus());

        List<OrderItemDTO> itemDTOS = order.getOrderItems().stream().map(item -> {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setFoodItemId(item.getFoodItems().getId());
            itemDTO.setFoodName(item.getFoodItems().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getPrice());
            return itemDTO;
        }).toList();
        dto.setItems(itemDTOS);
        return dto;
    }

    public void updateOrderStatus(Long orderId , String status){
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("order not found"));
        order.setStatus(status);
        ordersRepository.save(order);
    }

    public List<OrderDTO> getAllOrdersDTO(){
        return ordersRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public List<OrderDTO> getOrdersByStatus(String status){
        return ordersRepository.findByStatus(status).stream().map(this::convertToDTO).toList();
    }

    public List<Orders> getRecentOrders(){
        return ordersRepository.findTop10ByOrderByCreatedAtDesc();
    }

}
