package com.example.demo.repo;


import com.example.demo.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Long> {

    List<Category> findCategoryByName(String name);

    Optional<Category> findByName(String name);

    public Boolean existsByName(String name);

    public void deleteCategoryByName(String name);

}
