package com.example.cafemanagementsystem.repo;

import com.example.cafemanagementsystem.model.Users;
import com.example.cafemanagementsystem.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByEmail(String email);


    @Modifying
    @Transactional
    @Query(value = "update users set status=:status where id=:id", nativeQuery = true)
    void updateStatus(@Param("status") String status, @Param("id") int id);

    @Query(value = "select email from users where role='admin'",nativeQuery = true)
    List<String> findAllByRole();
}
