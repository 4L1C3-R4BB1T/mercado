package com.api.mercado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.mercado.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
