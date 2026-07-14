package com.example.demo.repo;


import com.example.demo.models.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payments , Long> {

    public Optional<Payments> findById(long id);

    public Boolean existsById(long id);

    public void deleteById(long id);
}
