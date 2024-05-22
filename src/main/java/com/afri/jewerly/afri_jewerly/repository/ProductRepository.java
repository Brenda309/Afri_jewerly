package com.afri.jewerly.afri_jewerly.repository;

import com.afri.jewerly.afri_jewerly.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface ProductRepository extends JpaRepository<Product, Long> {
    }

