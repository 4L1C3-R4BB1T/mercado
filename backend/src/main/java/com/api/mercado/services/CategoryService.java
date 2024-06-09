package com.api.mercado.services;

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
		category.setName(categoryRequest.getName());
		category.setDescription(categoryRequest.getDescription());
		return new CategoryDTO(repository.save(category));
	}

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAll(Pageable pageable) {
		return repository.findAll(pageable).map(x -> new CategoryDTO(x));
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		if (categoryNotExists(id)) {
			throw new RuntimeException("Categoria com id " + id + " não encontrada.");
		}
		return new CategoryDTO(repository.findById(id).get());
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryRequest categoryRequest) {
		if (categoryNotExists(id)) {
			throw new RuntimeException("Categoria com id " + id + " não encontrada.");
		}
		Category updateCategory = repository.findById(id).get();
		updateCategory.setName(categoryRequest.getName());
		updateCategory.setDescription(categoryRequest.getDescription());
		return new CategoryDTO(repository.save(updateCategory));
	}

	@Transactional
	public void delete(Long id) {
		if (categoryNotExists(id)) {
			throw new RuntimeException("Categoria com id " + id + " não encontrada.");
		}
		repository.deleteById(id);
	}

	private boolean categoryNotExists(Long id) {
		return !repository.findById(id).isPresent();
	}

}
