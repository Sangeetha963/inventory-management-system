package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.ProductDTO;
import com.example.employeemanagement.model.Product;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletResponse;


import java.util.List;

public interface ProductService {

    Product createProduct(ProductDTO dto);

    Product getProduct(Long id);

    List<Product> getAllProducts();

    Product updateProduct(Long id, ProductDTO dto);

    void deleteProduct(Long id);

    List<Product> getLowStockProducts();

    void uploadProductsFromExcel(MultipartFile file);

    List<Product> searchProducts(String name, String category, Double minPrice, Double maxPrice);

    void exportProductsToExcel(HttpServletResponse response);

    Product updateProductPrice(Long id, Double newPrice);

}
