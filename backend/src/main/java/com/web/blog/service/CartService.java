package com.web.blog.service;

import java.util.List;

import com.web.blog.dao.CartDao;
import com.web.blog.model.cart.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
	@Autowired
	CartDao cartDao;
	
	public List<Cart> findAllByUserid(int userid) {
		List<Cart> cart = cartDao.findAllByUserid(userid);
		return cart;
	}
	public void writeCart(Cart cart) {
		cartDao.save(cart);
	}
	public void deleteCart(int cid) {
		cartDao.deleteById(cid);
	}
}