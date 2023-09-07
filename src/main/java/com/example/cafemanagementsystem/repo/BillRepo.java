package com.example.cafemanagementsystem.repo;

import com.example.cafemanagementsystem.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepo extends JpaRepository<Bill,Integer> {
    List<Bill> findByCreatedBy(String userName);

}
