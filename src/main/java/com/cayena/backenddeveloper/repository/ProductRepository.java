package com.cayena.backenddeveloper.repository;

import com.cayena.backenddeveloper.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}