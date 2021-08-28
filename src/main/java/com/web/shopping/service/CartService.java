package com.web.shopping.service;

import com.web.shopping.dto.Cart;
import com.web.shopping.model.request.PaymentRequest;

import javax.servlet.http.HttpSession;

public interface CartService {

    public void addToCart(Long id,  HttpSession session);

    public void addCartByCount(HttpSession session, Long id, int count);

    public int countItemInCart(HttpSession session);

    public Cart getCart(HttpSession session);

    void addCount(HttpSession session, long id);

    void removeCount(HttpSession session, long id);

}
