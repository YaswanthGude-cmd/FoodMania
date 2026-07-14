package com.example.demo.service;


import com.example.demo.models.FoodItems;
import com.example.demo.models.OrderItems;
import com.example.demo.models.Orders;
import com.example.demo.repo.FoodItemsRepository;
import com.example.demo.repo.OrderItemsRepository;
import com.example.demo.repo.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemsRepository orderItemsRepository;
    private final OrdersRepository ordersRepository;
    private final FoodItemsRepository foodItemsRepository;

    @Autowired
    public OrderItemService(
            OrderItemsRepository orderItemsRepository,
            OrdersRepository ordersRepository,
            FoodItemsRepository foodItemsRepository
    ){
        this.orderItemsRepository = orderItemsRepository;
        this.ordersRepository = ordersRepository;
        this.foodItemsRepository = foodItemsRepository;
    }

    public void  createOrderItem(OrderItems  orderItems){
        if(orderItems.getOrder().getId() == null || orderItems.getFoodItems().getId() == null){
            throw new RuntimeException("orders id and food items id is required");
        }
        Orders order = ordersRepository.findById(orderItems.getOrder().getId())
                .orElseThrow(()-> new RuntimeException("order is not found"));

        FoodItems foodItems = foodItemsRepository.findById(orderItems.getFoodItems().getId())
                .orElseThrow(()-> new RuntimeException("food items is not found"));

        if(orderItems.getPrice() < 0){
            throw new RuntimeException("order price is negative");
        }
        if(orderItems.getQuantity() < 0){
            throw new RuntimeException("order quantity is negative");
        }

        orderItems.setOrder(order);
        orderItems.setFoodItems(foodItems);
        orderItemsRepository.save(orderItems);
    }

    public void updateOrderItem(Long id ,OrderItems  orderItems){
        Optional<OrderItems> tempOrderItems = orderItemsRepository.findById(id);

        if(tempOrderItems.isEmpty()){
            throw new RuntimeException("orderItem  not found");
        }else{
            OrderItems currentOrderItem = tempOrderItems.get();
            if(currentOrderItem.getOrder() != null){
                Long orderId = currentOrderItem.getOrder().getId();
                Orders  order = ordersRepository.findById(orderId).orElseThrow(()-> new RuntimeException("order is not found"));
                currentOrderItem.setOrder(order);
            }

            if(currentOrderItem.getFoodItems() != null){
                Long foodItemId = currentOrderItem.getFoodItems().getId();
                Optional<FoodItems> foodItems = foodItemsRepository.findById(foodItemId);
                if(foodItems.isEmpty()){
                    throw new RuntimeException("food items id not found");
                }
                FoodItems currentFoodItem = foodItems.get();
                currentOrderItem.setFoodItems(currentFoodItem);
            }

            if(currentOrderItem.getQuantity() < 0){
                throw new RuntimeException("order quantity is negative");
            }else{
                currentOrderItem.setQuantity(currentOrderItem.getQuantity() + orderItems.getQuantity());
            }
            if(currentOrderItem.getPrice() < 0){
                throw new RuntimeException("order price is negative");
            }else{
                currentOrderItem.setPrice(currentOrderItem.getPrice() + orderItems.getPrice());
            }
            orderItemsRepository.save(currentOrderItem);
        }
    }

    public List<OrderItems> getAllOrderItems(){
        return orderItemsRepository.findAll();
    }

    public void deleteOrderItem(Long id){
        if(id == null){
            throw new RuntimeException("id is required");
        }
        if(!orderItemsRepository.existsById(id)){
            throw new RuntimeException("order id not found");
        }
        orderItemsRepository.deleteById(id);
    }
}
