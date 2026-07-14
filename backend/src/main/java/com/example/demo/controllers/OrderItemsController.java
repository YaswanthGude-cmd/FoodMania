package com.example.demo.controllers;


import com.example.demo.models.OrderItems;
import com.example.demo.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orderitems")
public class OrderItemsController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemsController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public ResponseEntity<HashMap<String , String>> createOrderItems(@RequestBody OrderItems orderItems) {
        try{
            orderItemService.createOrderItem(orderItems);
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                    "status","1",
                    "message","order item created successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }

    @GetMapping
    public List<OrderItems> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String , String>> updateOrderItem(
            @PathVariable Long id,
            @RequestBody OrderItems orderItems) {
        try{
            orderItemService.updateOrderItem(id, orderItems);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status","1",
                    "message","order item updated successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String , String>> deleteOrderItem(@PathVariable Long id) {
        try{
            orderItemService.deleteOrderItem(id);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status","1",
                    "message","order item deleted successfully"
            )));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }
}
