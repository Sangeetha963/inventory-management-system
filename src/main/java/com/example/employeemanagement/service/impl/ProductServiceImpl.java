package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.dto.ProductDTO;
import com.example.employeemanagement.model.Product;
import com.example.employeemanagement.repository.ProductRepository;
import com.example.employeemanagement.service.ProductService;
import com.example.employeemanagement.model.PriceHistory;
import com.example.employeemanagement.repository.PriceHistoryRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.time.LocalDateTime;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;
    private final PriceHistoryRepository priceHistoryRepo;


      public ProductServiceImpl(ProductRepository repo, PriceHistoryRepository priceHistoryRepo) {
        this.repo = repo;
        this.priceHistoryRepo = priceHistoryRepo;
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

    @Override
    public List<Product> searchProducts(String name, String category, Double minPrice, Double maxPrice) {
        return repo.searchProducts(name, category, minPrice, maxPrice);
    }

    @Override
    public void exportProductsToExcel(HttpServletResponse response) {
        try {
            List<Product> products = repo.findAll();

            products.sort(Comparator.comparing(Product::getId));

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Products");

            /* ----------------------  STYLES  ------------------------ */

            // Header Style: Bold + Background color + Center
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Normal Data Style: Border only
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            /* --------------------  HEADER ROW  ----------------------- */
            Row header = sheet.createRow(0);

            String[] columns = {"ID", "Name", "Price", "Category", "Stock"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            /* --------------------  DATA ROWS  ------------------------ */
            int rowIndex = 1;
            for (Product p : products) {
                Row row = sheet.createRow(rowIndex++);

                Cell c0 = row.createCell(0);
                c0.setCellValue(p.getId() != null ? p.getId() : 0);
                c0.setCellStyle(dataStyle);

                Cell c1 = row.createCell(1);
                c1.setCellValue(p.getName() != null ? p.getName() : "");
                c1.setCellStyle(dataStyle);

                Cell c2 = row.createCell(2);
                c2.setCellValue(p.getPrice() != null ? p.getPrice() : 0.0);
                c2.setCellStyle(dataStyle);

                Cell c3 = row.createCell(3);
                c3.setCellValue(p.getCategory() != null ? p.getCategory() : "");
                c3.setCellStyle(dataStyle);

                Cell c4 = row.createCell(4);
                c4.setCellValue(p.getStock() != null ? p.getStock() : 0);
                c4.setCellStyle(dataStyle);
            }

            /* ------------------  AUTO SIZE COLUMNS  ------------------ */
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            /* ----------------------  DOWNLOAD  ------------------------ */
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");

            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (Exception e) {
            throw new RuntimeException("Failed to export products", e);
        }
    }


    @Transactional
    @Override
    public Product updateProductPrice(Long id, Double newPrice) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Double oldPrice = product.getPrice();      // Hold previous price
        product.setPrice(newPrice);                // Update new price
        repo.save(product);                        // Save product

        // Create price history log
        PriceHistory history = new PriceHistory();
        history.setProductId(product.getId());
        history.setOldPrice(oldPrice);
        history.setNewPrice(newPrice);
        history.setChangedAt(LocalDateTime.now().toString());

        priceHistoryRepo.save(history);            // Save log entry

        return product;
    }

}
