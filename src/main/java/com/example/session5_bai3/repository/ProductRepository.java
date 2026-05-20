package com.example.session5_bai3.repository;


import com.example.session5_bai3.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}