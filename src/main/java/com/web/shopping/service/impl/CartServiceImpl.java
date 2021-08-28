package com.web.shopping.service.impl;

import com.web.shopping.dto.Cart;
import com.web.shopping.dto.OrderLine;
import com.web.shopping.model.entity.Product;
import com.web.shopping.model.request.PaymentRequest;
import com.web.shopping.service.CartService;
import com.web.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ProductService productService;

    //add
    public void addToCart(Long id,  HttpSession session) {
        HashMap<Long, OrderLine> cart;

        var rawCart = session.getAttribute("CART");

        if (rawCart instanceof HashMap) {
            cart = (HashMap<Long, OrderLine>) rawCart;
        } else {
            cart = new HashMap<>();
        }

        Optional<Product> product = Optional.ofNullable(productService.showById(id));
        if (product.isPresent()) {
            OrderLine orderLine = cart.get(id);
            if (orderLine == null) {
                cart.put(id, new OrderLine(product.get(), 1));
            } else {
                orderLine.increaseByOne();
                cart.put(id, orderLine);
            }
        }
        session.setAttribute("CART", cart);
    }
    //add cart by count > 1 from product detail
    @Override
    public void addCartByCount(HttpSession session, Long id, int count) {
        HashMap<Long, OrderLine> cart= (HashMap<Long, OrderLine>) session.getAttribute("CART");
        OrderLine orderLine = cart.get(id);
        if(orderLine != null){
            orderLine.setCount(orderLine.getCount()+count);
        }else {
            Optional<Product> product = Optional.ofNullable(productService.showById(id));
            cart.put(id, new OrderLine(product.get(), count));
        }
        session.setAttribute("CART", cart);
    }

    //count Item
    public int countItemInCart(HttpSession session) {
        HashMap<Long, OrderLine> cart;

        var rawCart = session.getAttribute("CART");

        if (rawCart instanceof HashMap) {
            cart = (HashMap<Long, OrderLine>) rawCart;
            return cart.values().stream().mapToInt(OrderLine::getCount).sum();
        } else {
            return 0;
        }
    }
    //getCart
    public Cart getCart(HttpSession session) {
        HashMap<Long, OrderLine> cart;

        var rawCart = session.getAttribute("CART");

        if (rawCart instanceof HashMap) {
            cart = (HashMap<Long, OrderLine>) rawCart;
            return new Cart(
                    cart.values().stream().collect(Collectors.toList()),  //danh sách các mặt hàng mua
                    0.01f, //%Giảm giá
                    true   //Có tính thuế VAT không?
            );
        } else {
            return new Cart();
        }
    }
    //add count
    @Override
    public void addCount(HttpSession session, long id) {
        HashMap<Long, OrderLine> cart= (HashMap<Long, OrderLine>) session.getAttribute("CART");
        OrderLine orderLine = cart.get(id);
        orderLine.setCount(orderLine.getCount()+1);
        session.setAttribute("CART", cart);
    }
    //remove item
    @Override
    public void removeCount(HttpSession session, long id) {
        HashMap<Long, OrderLine> cart= (HashMap<Long, OrderLine>) session.getAttribute("CART");
        OrderLine orderLine = cart.get(id);
        if(orderLine.getCount() > 1){
            orderLine.setCount(orderLine.getCount()-1);
        }
        session.setAttribute("CART", cart);
    }


}
