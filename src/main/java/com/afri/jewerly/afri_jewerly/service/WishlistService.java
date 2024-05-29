package com.afri.jewerly.afri_jewerly.service;

import com.afri.jewerly.afri_jewerly.entity.Product;
import com.afri.jewerly.afri_jewerly.entity.Users;
import com.afri.jewerly.afri_jewerly.entity.Wishlist;
import com.afri.jewerly.afri_jewerly.repository.ProductRepository;
import com.afri.jewerly.afri_jewerly.repository.UserRepository;
import com.afri.jewerly.afri_jewerly.repository.WishlistRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public void addToWishlist(Long userId, Long productId) {
        Users user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseGet(() -> new Wishlist());

        wishlist.getProducts().add(product);
        wishlistRepository.save(wishlist);
    }
}
