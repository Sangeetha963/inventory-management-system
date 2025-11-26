package com.example.employeemanagement.listener;

import com.example.employeemanagement.model.Product;
import com.example.employeemanagement.model.ProductAudit;
import com.example.employeemanagement.repository.ProductAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.persistence.*;


import java.time.LocalDateTime;

@Component
public class ProductAuditListener {

    private static ProductAuditRepository repository;

    @Autowired
    public void init(ProductAuditRepository repo){
        repository = repo;
    }

    @PostPersist
    public void onCreate(Product p){
        saveAudit(p,"CREATE",null,p.toString());
    }

    @PostUpdate
    public void onUpdate(Product p){
        saveAudit(p,"UPDATE","OLD VALUE CHANGED",p.toString());
    }

    @PostRemove
    public void onDelete(Product p){
        saveAudit(p,"DELETE",p.toString(),null);
    }

    private void saveAudit(Product p,String action,String oldValue,String newValue){
        ProductAudit audit = new ProductAudit();
        audit.setProductId(p.getId());
        audit.setAction(action);
        audit.setOldValue(oldValue);
        audit.setNewValue(newValue);
        audit.setChangedAt(LocalDateTime.now());

        repository.save(audit);
    }
}
