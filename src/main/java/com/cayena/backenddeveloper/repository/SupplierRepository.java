package com.cayena.backenddeveloper.repository;

import com.cayena.backenddeveloper.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
