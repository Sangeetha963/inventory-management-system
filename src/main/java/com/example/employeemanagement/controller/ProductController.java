package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.ProductDTO;
import com.example.employeemanagement.model.Product;
import com.example.employeemanagement.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product create(@RequestBody ProductDTO dto) {
        return service.createProduct(dto);
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return service.getProduct(id);
    }

    @GetMapping
    public List<Product> all() {
        return service.getAllProducts();
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return service.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteProduct(id);
        return "Deleted Successfully";
    }

    @GetMapping("/low-stock")
    public List<Product> getLowStockProducts(){
        return service.getLowStockProducts();
    }
}
