package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.ProductDTO;
import com.example.employeemanagement.model.Product;
import com.example.employeemanagement.service.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import com.example.employeemanagement.model.ProductAudit;
import com.example.employeemanagement.listener.ProductAuditListener;


import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // -------- Create Product --------
    @PostMapping
    public Product create(@RequestBody ProductDTO dto) {
        return service.createProduct(dto);
    }

    // -------- Get Single Product --------
    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return service.getProduct(id);
    }

    // -------- Get All Products --------
    @GetMapping
    public List<Product> all() {
        return service.getAllProducts();
    }

    // -------- Update Product --------
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return service.updateProduct(id, dto);
    }

    // -------- Delete Product --------
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteProduct(id);
        return "Deleted Successfully";
    }

    // -------- Get Low Stock Products --------
    @GetMapping("/low-stock")
    public List<Product> getLowStockProducts(){
        return service.getLowStockProducts();
    }

    // -------- Import Excel --------
    @PostMapping("/upload")
    public String uploadExcel(@RequestParam("file") MultipartFile file) {
        service.uploadProductsFromExcel(file);
        return "Uploaded Successfully";
    }

    // -------- Search Products --------
    @GetMapping("/search")
    public List<Product> searchProducts(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice
    ) {
        return service.searchProducts(name, category, minPrice, maxPrice);
    }

    // -------- Export Excel --------
    @GetMapping("/export")
    public void exportProducts(HttpServletResponse response) {
        service.exportProductsToExcel(response);
    }

    // -------- Update Product Price (Fixes 404 error) --------
    @PutMapping("/{id}/price")
    public ResponseEntity<Product> updatePrice(
            @PathVariable Long id,
            @RequestParam Double price) {

        Product updated = service.updateProductPrice(id, price);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/audit")
    public List<ProductAudit> getAudit(@PathVariable Long id){
        return service.getAuditLogs(id);
    }
}
