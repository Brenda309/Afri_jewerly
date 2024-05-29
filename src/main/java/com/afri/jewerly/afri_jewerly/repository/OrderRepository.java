package com.afri.jewerly.afri_jewerly.repository;

import com.afri.jewerly.afri_jewerly.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
