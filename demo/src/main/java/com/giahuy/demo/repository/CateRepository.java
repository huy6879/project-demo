package com.giahuy.demo.repository;

import com.giahuy.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CateRepository extends JpaRepository<Category, String> {

}
