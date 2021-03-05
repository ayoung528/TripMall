package com.web.blog.dao;

import java.util.List;

import com.web.blog.model.cart.Cart;

import org.springframework.data.jpa.repository.JpaRepository;



public interface CartDao extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserid(int userid);
}