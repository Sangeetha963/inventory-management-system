package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.ProductDTO;
import com.example.employeemanagement.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductDTO dto);

    Product getProduct(Long id);

    List<Product> getAllProducts();

    Product updateProduct(Long id, ProductDTO dto);

    void deleteProduct(Long id);
}
