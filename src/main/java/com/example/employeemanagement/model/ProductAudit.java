package com.example.employeemanagement.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_audit")
public class ProductAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String action;        // CREATE / UPDATE / DELETE
    private String oldValue;
    private String newValue;
    private LocalDateTime changedAt;

    public ProductAudit(){}

    public ProductAudit(Long productId, String action, String oldValue, String newValue){
        this.productId = productId;
        this.action = action;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.changedAt = LocalDateTime.now();
    }

    // -------- getters/setters --------
    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public String getAction() { return action; }
    public String getOldValue() { return oldValue; }
    public String getNewValue() { return newValue; }
    public LocalDateTime getChangedAt() { return changedAt; }

    public void setProductId(Long productId) { this.productId = productId; }
    public void setAction(String action) { this.action = action; }
    public void setOldValue(String oldValue) { this.oldValue = oldValue; }
    public void setNewValue(String newValue) { this.newValue = newValue; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }
}
