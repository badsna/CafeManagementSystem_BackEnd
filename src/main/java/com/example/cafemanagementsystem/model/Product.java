package com.example.cafemanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.security.PrivateKey;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class Product {
    public static final long serialVerisonUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    //one product can refer to multiple category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk",nullable = false)
    private Category category;

    private String description;
    private Integer price;
    private String status;
}
