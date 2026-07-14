package com.example.demo.controllers;


import com.example.demo.models.Payments;
import com.example.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payments> getAllPayments(){
        return paymentService.getAllPayments();
    }

    @PostMapping
    public ResponseEntity<HashMap<String , String >> postPayments(@RequestBody Payments payments){
        try{
            paymentService.postPayment(payments);
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                    "status" , "1",
                    "message","payments posted successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status" , "0",
                    "message",e.getMessage()
            )));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String , String >> putPayments(@PathVariable Long id, @RequestBody Payments payments){
        try{
            paymentService.updatePayments(id, payments);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status" , "1",
                    "message","payments updated successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String , String >> deletePayments(@PathVariable Long id){
        try{
            paymentService.deletePayment(id);
            return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>(Map.of(
                    "status","1",
                    "message","payments deleted successfully"
            )));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>(Map.of(
                    "status","0",
                    "message",e.getMessage()
            )));
        }
    }
}
