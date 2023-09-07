package com.example.cafemanagementsystem.repo;

import com.example.cafemanagementsystem.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Modifying
    @Transactional
    @Query(value = "update product set status=:status where id=:id", nativeQuery = true)
    Integer updateProductStatus(@Param("status") String status,@Param("id") int id);

    List<Product> findByCategory_Id(@Param("id") int id);
}
