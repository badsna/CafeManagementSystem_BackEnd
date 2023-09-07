package com.example.cafemanagementsystem.service;

import com.example.cafemanagementsystem.model.Category;
import com.example.cafemanagementsystem.model.Product;
import com.example.cafemanagementsystem.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseEntity<String> addNewProduct(Map<String, String> productRequestDto);
    ResponseEntity<List<ProductWrapper>> getAllProduct();

    ResponseEntity<String> updateProduct(Map<String, String> productRequestDto);

    ResponseEntity<String> deleteProduct(Integer id);
    ResponseEntity<String> updateStatus(Map<String, String> productRequestDto);

    ResponseEntity<List<ProductWrapper>> getByCategoryId(Integer id);

    ResponseEntity<ProductWrapper> getByProductId(Integer id);
}
