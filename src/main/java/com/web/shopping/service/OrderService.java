package com.web.shopping.service;

import com.web.shopping.model.entity.Order;
import com.web.shopping.model.entity.OrderDetail;
import com.web.shopping.model.entity.Product;
import com.web.shopping.model.request.PaymentRequest;
import com.web.shopping.model.response.OrderResponse;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface OrderService {
    Order save(PaymentRequest request, HttpSession session);

    List<Product> findByOrder(long id);
}
