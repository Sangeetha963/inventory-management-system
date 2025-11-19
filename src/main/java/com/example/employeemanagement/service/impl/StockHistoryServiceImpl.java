package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.model.StockHistory;
import com.example.employeemanagement.repository.StockHistoryRepository;
import com.example.employeemanagement.service.StockHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockHistoryServiceImpl implements StockHistoryService {

    private final StockHistoryRepository repo;

    public StockHistoryServiceImpl(StockHistoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<StockHistory> getStockHistory(Long productId) {
        return repo.findByProductId(productId);
    }
}
