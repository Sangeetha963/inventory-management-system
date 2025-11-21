package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.ProductDTO;
import com.example.employeemanagement.model.Product;
import com.example.employeemanagement.repository.ProductRepository;
import com.example.employeemanagement.service.ProductService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;


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
    
   @Override
    public List<Product> getLowStockProducts() {
        return repo.findAll()
                .stream()
                .filter(p -> p.getStock() != null && p.getStock() < 10)
                .collect(Collectors.toList());
    }

   @Override
    public void uploadProductsFromExcel(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {   // skip header
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String name = row.getCell(0).getStringCellValue();
                Double price = row.getCell(1).getNumericCellValue();
                String category = row.getCell(2).getStringCellValue();
                Integer stock = (int) row.getCell(3).getNumericCellValue();

                Product p = new Product(name, price, category, stock);
                repo.save(p);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload Excel", e);
        }
    }

}
