package com.api.mercado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.mercado.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
