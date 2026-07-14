package com.example.demo.repo;


import com.example.demo.models.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

    public Optional<OrderItems> findById(long id);

    public Boolean existsById(long id);

    public void deleteById(long id);

}
