package com.afri.jewerly.afri_jewerly.service;

import com.afri.jewerly.afri_jewerly.entity.CartItem;
import com.afri.jewerly.afri_jewerly.entity.Order;
import com.afri.jewerly.afri_jewerly.entity.Product;
import com.afri.jewerly.afri_jewerly.repository.OrderRepository;
import com.afri.jewerly.afri_jewerly.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private ProductRepository productRepository; // Properly autowire the ProductRepository


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {
        for (CartItem item : order.getItems()) {
            // Ensure each CartItem references a valid Product
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            item.setProduct(product);
            item.setOrder(order);
        }
        return orderRepository.save(order);
    }
}
