package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.model.Supplier;
import com.example.employeemanagement.repository.SupplierRepository;
import com.example.employeemanagement.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository repo;

    public SupplierServiceImpl(SupplierRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return repo.findAll();
    }
}
