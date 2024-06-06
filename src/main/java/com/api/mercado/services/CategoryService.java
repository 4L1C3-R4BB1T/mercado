package com.api.mercado.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.mercado.entities.Category;
import com.api.mercado.models.CategoryDTO;
import com.api.mercado.models.requests.CategoryRequest;
import com.api.mercado.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository repository;

	@Transactional
	public CategoryDTO create(CategoryRequest categoryRequest) {
		Category category = new Category();
		category.setName(categoryRequest.name());
		category.setDescription(categoryRequest.description());
		repository.save(category);
		return new CategoryDTO(category);
	}

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAll(Pageable pageable) {
		Page<Category> categories = repository.findAll(pageable);
		return categories.map(x -> new CategoryDTO(x));
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> category = repository.findById(id);
		if (category.isPresent()) {
			return new CategoryDTO(category.get());
		} 
		throw new RuntimeException("Categoria com id " + id + " não encontrada");
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryRequest categoryRequest) {
		Optional<Category> category = repository.findById(id);
		if (category.isPresent()) {
			Category updateCategory = category.get();
			updateCategory.setName(categoryRequest.name());
			updateCategory.setDescription(categoryRequest.description());
			repository.save(updateCategory);
			return new CategoryDTO(updateCategory);
		} 
		throw new RuntimeException("Categoria com id " + id + " não encontrada");
	}

	@Transactional
	public void delete(Long id) {
		Optional<Category> category = repository.findById(id);
		if (category.isPresent()) {
			repository.deleteById(id);
			return;
		}
		throw new RuntimeException("Categoria com id " + id + " não encontrada");
	}

}
