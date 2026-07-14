package com.example.demo.service;


import com.example.demo.models.Orders;
import com.example.demo.models.Payments;
import com.example.demo.repo.OrdersRepository;
import com.example.demo.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrdersRepository ordersRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, OrdersRepository ordersRepository) {
        this.paymentRepository = paymentRepository;
        this.ordersRepository = ordersRepository;
    }

    public void postPayment(Payments payments){
        if(payments.getOrder().getId() ==  null){
            throw new RuntimeException("order id is  required");
        }
        Orders order = ordersRepository.findById(payments.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("order not found"));

        if(payments.getAmount() < 0){
            throw new RuntimeException("amount is negative");
        }

        payments.setOrder(order);
        paymentRepository.save(payments);
    }

    public List<Payments> getAllPayments(){
        return paymentRepository.findAll();
    }

    public void updatePayments(Long id , Payments payments){
        Optional<Payments> tempPayments = paymentRepository.findById(id);
        if(tempPayments.isEmpty()){
            throw new RuntimeException("payment not found");
        }else{
            Payments currentPayment = tempPayments.get();
            if(currentPayment.getOrder() != null){
                Long orderId = currentPayment.getOrder().getId();
                Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("order not found"));
                payments.setOrder(order);
            }
            if(currentPayment.getAmount() < 0){
                throw new RuntimeException("amount is negative");
            }
            currentPayment.setAmount(payments.getAmount());
            if(currentPayment.getPaymentStatus() != null && !currentPayment.getPaymentStatus().isEmpty()){
                currentPayment.setPaymentStatus(payments.getPaymentStatus());
            }
            if(currentPayment.getPaymentMethod() != null && !currentPayment.getPaymentMethod().isEmpty()){
                currentPayment.setPaymentMethod(payments.getPaymentMethod());
            }
            paymentRepository.save(currentPayment);
        }
    }

    public void deletePayment(Long id){
        if(id == null){
            throw new RuntimeException("id can't be null");
        }
        if(!paymentRepository.existsById(id)){
            throw new RuntimeException("payment not found");
        }
        paymentRepository.deleteById(id);
    }
}
