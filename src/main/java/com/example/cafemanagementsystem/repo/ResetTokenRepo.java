package com.example.cafemanagementsystem.repo;

import com.example.cafemanagementsystem.model.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResetTokenRepo extends JpaRepository<ResetToken, Integer> {
    @Query(value = "select * from reset_token where token=?1", nativeQuery = true)
    ResetToken findByToken(String token);

    void deleteByToken(String token);

    ResetToken findByEmail(String email);
}
