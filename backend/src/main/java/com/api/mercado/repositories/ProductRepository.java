package com.api.mercado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.api.mercado.entities.Category;
import com.api.mercado.entities.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

}
