package com.api.mercado.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.mercado.entities.Category;
import com.api.mercado.entities.Product;
import com.api.mercado.models.ProductDTO;
import com.api.mercado.models.requests.ProductRequest;
import com.api.mercado.repositories.CategoryRepository;
import com.api.mercado.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository repository;
	private final CategoryRepository categoryRepository;

	@Transactional
	public ProductDTO create(ProductRequest productRequest) {
		Optional<Category> category = categoryRepository.findById(productRequest.getCategoryId());
		if (!category.isPresent()) {
			throw new RuntimeException("Categoria com id " + productRequest.getCategoryId() + " não encontrada.");
		}
		Product product = new Product();
		product.setName(productRequest.getName());
		product.setDescription(productRequest.getDescription());
		product.setPrice(productRequest.getPrice());
		product.setStock(productRequest.getStock());
		product.setCategory(category.get());
		return new ProductDTO(repository.save(product));
	}

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		return repository.findAll(pageable).map(x -> new ProductDTO(x));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		if (productNotExists(id)) {
			throw new RuntimeException("Produto com id " + id + " não encontrado.");
		}
		return new ProductDTO(repository.findById(id).get());
	}

	@Transactional
	public ProductDTO update(Long id, ProductRequest productRequest) {
		if (productNotExists(id)) {
			throw new RuntimeException("Produto com id " + id + " não encontrado.");
		}
		Optional<Category> category = categoryRepository.findById(productRequest.getCategoryId());
		if (!categoryRepository.findById(productRequest.getCategoryId()).isPresent()) {
			throw new RuntimeException("Categoria com id " + productRequest.getCategoryId() + " não encontrada.");
		}
		Product updateProduct = repository.findById(id).get();
		updateProduct.setName(productRequest.getName());
		updateProduct.setDescription(productRequest.getDescription());
		updateProduct.setPrice(productRequest.getPrice());
		updateProduct.setStock(productRequest.getStock());
		updateProduct.setCategory(category.get());
		return new ProductDTO(repository.save(updateProduct));
	}

	@Transactional
	public void delete(Long id) {
		if (productNotExists(id)) {
			throw new RuntimeException("Produto com id " + id + " não encontrado.");
		}
		repository.deleteById(id);
	}

	private boolean productNotExists(Long id) {
		return !repository.findById(id).isPresent();
	}

}
