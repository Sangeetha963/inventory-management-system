package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.ProductAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductAuditRepository extends JpaRepository<ProductAudit, Long> {

    List<ProductAudit> findByProductId(Long productId);
}
