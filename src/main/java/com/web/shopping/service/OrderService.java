package com.web.shopping.service;

import com.web.shopping.model.entity.Order;
import com.web.shopping.model.entity.OrderDetail;
import com.web.shopping.model.request.PaymentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(PaymentRequest request, HttpSession session);

   Order searchOrderById(String id);

    List<OrderDetail> findOrderId(String id);

    List<Order> getAll();

    Optional<Page<Order>> list(Pageable pageable);

    void updateStatusOrder(Long id, int status);

    List<Order> searchByOrderStatus(int status);
}
