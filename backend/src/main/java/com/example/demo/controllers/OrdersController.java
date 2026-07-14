package com.example.demo.controllers;


import com.example.demo.dto.OrderDTO;
import com.example.demo.models.Orders;
import com.example.demo.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    public List<Orders> getOrders(){
        return ordersService.getAllOrders();
    }

    @PostMapping
    public ResponseEntity<HashMap<String , String>> createOrder(@RequestBody Orders  orders){
        try{
            ordersService.createOrder(orders);
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                    "status","1",
                    "message","order placed successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status", "0",
                    "message",e.getMessage()
            )));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String , String>> updateOrder(
            @PathVariable Long id,
            @RequestBody Orders  orders
    ){
        try{
            ordersService.updateOrder(id , orders);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status", "1",
                    "message","order updated successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String , String>> deleteOrder(@PathVariable Long id){
        try{
            ordersService.deleteOrder(id);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status", "1",
                    "message","order deleted successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status", "0",
                    "message",e.getMessage()
            )));
        }
    }

    @PostMapping("/place-order/{userId}")
    public ResponseEntity<?> placeOrder(@PathVariable Long userId){
        ordersService.placeOrder(userId);
        return ResponseEntity.ok("order placed successfully");
    }

    @GetMapping("/admin-orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        return ResponseEntity.ok(ordersService.getAllOrdersDTO());
    }

    @PutMapping("/{orderId}/status/{status}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @PathVariable String status){
        try{
            ordersService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok("status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    e.getMessage()
            );
        }
    }

    @GetMapping("/admin-orders/{status}")
    public ResponseEntity<List<OrderDTO>> getAllOrdersByStatus(@PathVariable String status){
        return ResponseEntity.ok(ordersService.getOrdersByStatus(status));
    }

    @GetMapping("/admin/recent-orders")
    public List<Orders> getRecentOrders(){
        return ordersService.getRecentOrders();
    }
}
