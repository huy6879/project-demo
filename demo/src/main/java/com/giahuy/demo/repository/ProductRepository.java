package com.giahuy.demo.repository;

import com.giahuy.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String>{
    boolean existsByName(String name);

//    List<Product> searchByKw(String keyword);

}
