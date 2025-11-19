package com.example.employeemanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplier_id;

    private String supplier_name;
    private String contact_email;

    public Supplier() {}

    // Getters & Setters
    public Long getId() {
        return supplier_id;
    }

    public String getSupplierName() {
        return supplier_name;
    }

    public String getContactNumber() {
        return contact_email;
    }

    public void setSupplierName(String supplierName) {
        this.supplier_name = supplier_name;
    }

    public void setContactNumber(String contactNumber) {
        this.contact_email = contact_email;
    }
}
