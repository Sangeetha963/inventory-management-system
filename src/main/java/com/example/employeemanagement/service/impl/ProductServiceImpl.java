package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.ProductDTO;
import com.example.employeemanagement.model.Product;
import com.example.employeemanagement.repository.ProductRepository;
import com.example.employeemanagement.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public Product createProduct(ProductDTO dto) {
        Product p = new Product(dto.getName(), dto.getPrice(), dto.getCategory(), dto.getStock());
        return repo.save(p);
    }

    @Override
    public Product getProduct(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @Override
    public Product updateProduct(Long id, ProductDTO dto) {
        Product p = repo.findById(id).orElse(null);
        if (p == null) return null;

        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        p.setCategory(dto.getCategory());
        p.setStock(dto.getStock());

        return repo.save(p);
    }

    @Override
    public void deleteProduct(Long id) {
        repo.deleteById(id);
    }
}
