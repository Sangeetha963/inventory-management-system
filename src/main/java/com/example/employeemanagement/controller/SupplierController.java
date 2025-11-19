package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.Supplier;
import com.example.employeemanagement.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }

    @GetMapping
    public List<Supplier> getAll() {
        return service.getAllSuppliers();
    }
}
