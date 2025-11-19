package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
    List<StockHistory> findByProductId(Long productId);
}

