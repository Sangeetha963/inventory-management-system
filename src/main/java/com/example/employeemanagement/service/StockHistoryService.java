package com.example.employeemanagement.service;

import com.example.employeemanagement.model.StockHistory;
import java.util.List;


public interface StockHistoryService {
    List<StockHistory> getStockHistory(Long productId);
}

