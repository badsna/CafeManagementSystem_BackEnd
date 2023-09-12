package com.example.cafemanagementsystem.repo;

import com.example.cafemanagementsystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    //JPQL
    @Query(value = "select c from Category c where c.id in (select p.category from Product p where p.status='true')")
    List<Category> getAllCategory();
}
