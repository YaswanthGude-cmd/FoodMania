package com.example.demo.service;


import com.example.demo.models.Category;
import com.example.demo.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {


    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(Category category){
        if(categoryRepository.existsByName(category.getName())){
            throw new RuntimeException("Category already exists");
        }
        categoryRepository.save(category);
    }

    public void updateCategory( Long Id , Category category){

        Optional<Category> tempCategory = categoryRepository.findById(Id);

        if(tempCategory.isEmpty()){
            throw new RuntimeException("Category not found");
        }

        Category currCategory = tempCategory.get();

        if(category.getName() != null && !category.getName().isEmpty()){
            currCategory.setName(category.getName());
        }

        categoryRepository.save(currCategory);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public void deleteCategory(Long id){
        if(id == null){
            throw new RuntimeException("Invalid id");
        }
        if(!categoryRepository.existsById(id)){
            throw new RuntimeException("category is not found");
        }
        categoryRepository.deleteById(id);
    }

}
