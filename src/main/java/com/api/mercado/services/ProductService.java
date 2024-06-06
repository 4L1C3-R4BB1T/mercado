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
		Optional<Category> category = categoryRepository.findById(productRequest.categoryId());
		if (!category.isPresent()) {
			throw new RuntimeException("Categoria com id " + productRequest.categoryId() + " não encontrada");
		}
		Product product = new Product();
		product.setName(productRequest.name());
		product.setDescription(productRequest.description());
		product.setPrice(productRequest.price());
		product.setStock(productRequest.stock());
		product.setCategory(category.get());
		repository.save(product);
		return new ProductDTO(product);
	}

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		return repository.findAll(pageable).map(x -> new ProductDTO(x));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		if (productNotExists(id)) {
			throw new RuntimeException("Product com id " + id + " não encontrado.");
		}
		return new ProductDTO(repository.findById(id).get());
	}

	@Transactional
	public ProductDTO update(Long id, ProductRequest productRequest) {
		if (productNotExists(id)) {
			throw new RuntimeException("Product com id " + id + " não encontrado.");
		}
		Optional<Category> category = categoryRepository.findById(productRequest.categoryId());
		if (!categoryRepository.findById(productRequest.categoryId()).isPresent()) {
			throw new RuntimeException("Categoria com id " + productRequest.categoryId() + " não encontrada.");
		}
		Product updateProduct = repository.findById(id).get();
		updateProduct.setName(productRequest.name());
		updateProduct.setDescription(productRequest.description());
		updateProduct.setPrice(productRequest.price());
		updateProduct.setStock(productRequest.stock());
		updateProduct.setCategory(category.get());
		repository.save(updateProduct);
		return new ProductDTO(updateProduct);
	}

	@Transactional
	public void delete(Long id) {
		if (productNotExists(id)) {
			throw new RuntimeException("Product com id " + id + " não encontrado.");
		}
		repository.deleteById(id);
	}

	private boolean productNotExists(Long id) {
		return !repository.findById(id).isPresent();
	}

}
