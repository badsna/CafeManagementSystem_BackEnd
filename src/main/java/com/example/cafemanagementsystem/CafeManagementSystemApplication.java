package com.example.cafemanagementsystem;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class CafeManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeManagementSystemApplication.class, args);
    }

}
