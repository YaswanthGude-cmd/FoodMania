package com.example.demo.repo;

import com.example.demo.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    public Optional<Orders> findById(Long id);

    public Boolean existsById(long id);

    void deleteById(Long id);

    List<Orders> findByStatus(String status);

    @Query("SELECT COALESCE(SUM(o.totalPrice),0) FROM Orders o")
    Double getRevenue();

    List<Orders> findTop10ByOrderByCreatedAtDesc();
}
