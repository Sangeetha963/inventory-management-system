package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.StockHistory;
import com.example.employeemanagement.service.StockHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-history")
public class StockHistoryController {

    private final StockHistoryService service;

    public StockHistoryController(StockHistoryService service) {
        this.service = service;
    }

    @GetMapping("/product/{id}")
    public List<StockHistory> getHistory(@PathVariable Long id) {
        return service.getStockHistory(id);
    }
}
