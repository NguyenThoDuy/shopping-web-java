package com.web.shopping.service.impl;

import com.web.shopping.Repository.*;
import com.web.shopping.dto.Cart;
import com.web.shopping.dto.OrderLine;
import com.web.shopping.model.entity.Order;
import com.web.shopping.model.entity.OrderDetail;
import com.web.shopping.model.entity.Product;
import com.web.shopping.model.request.OrderRequest;
import com.web.shopping.model.request.PaymentRequest;
import com.web.shopping.model.response.OrderResponse;
import com.web.shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final OrderDetailRepo orderDetailRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final CatalogRepo catalogRepo;
    long millis = System.currentTimeMillis();
    public java.sql.Date date=new java.sql.Date(millis);

    @Override
    public Order save(PaymentRequest request, HttpSession session) {
        Order order_payment = null;
        OrderRequest orderRequest = convertOrder(request, session);
        try {
            Order order = new Order();
            order.setUsername(orderRequest.getName());
            order.setPhone(orderRequest.getPhone());
            order.setEmail(orderRequest.getEmail());
            order.setAddress(orderRequest.getAddress());
            order.setPayment(0);
            order.setStatus(0);
            order.setTotalprice(orderRequest.getTotalPrice());
            order.setCreated_at(date);
            //
            order_payment = orderRepo.save(order);
//        System.out.println(orderId);

            List<OrderLine> orderLines = orderRequest.getOrderLines();
            for (OrderLine orderLine : orderLines) {
                OrderDetail orderDetail= new OrderDetail();
                Product product = productRepo.getById(orderLine.getProduct().getId());
                product.setNumber_out(orderLine.getCount());
                productRepo.save(product);
                orderDetail.setOrder(order_payment);
                orderDetail.setProduct(product);
                orderDetail.setQty(orderLine.getCount());
                orderDetail.setTotalprice(orderLine.getProduct().getSale_price() * orderLine.getCount());
                //
                orderDetailRepo.save(orderDetail);
            }
        }catch (Exception ex){
            log.info(ex.getMessage());
        }
        return order_payment;
    }

    @Override
    public List<Product> findByOrder(long id) {
       List<Product> products = null;
       String ids = String.valueOf(id);
        try {
           products = catalogRepo.findProductsByCatalogId(ids);
        }catch (Exception ex){
            log.info(ex.getMessage());
        }

        return products;
    }

   public List<OrderDetail> showOrderDetail(Long id) {
        List<OrderDetail> orderDetails = null;
        Order order = orderRepo.getById(id);
        try {
            orderDetails = orderDetailRepo.findByOrder(order);
        }catch (Exception ex){
            log.info(ex.getMessage());
        }

        return orderDetails;
    }

    private OrderRequest convertOrder(PaymentRequest request, HttpSession session) {
        HashMap<Long, OrderLine> cart= (HashMap<Long, OrderLine>) session.getAttribute("CART");
        Cart orderCart = new Cart(
                cart.values().stream().collect(Collectors.toList()),  //danh sách các mặt hàng mua
                0.01f, //%Giảm giá
                true   //Có tính thuế VAT không?
        );
        OrderRequest paymentRequest = new OrderRequest();
        paymentRequest.setUser_id(request.getUser_id());
        paymentRequest.setName(request.getUsername());
        paymentRequest.setPhone(request.getPhone());
        paymentRequest.setEmail(request.getEmail());
        paymentRequest.setAddress(request.getAddress());
        paymentRequest.setTotalPrice(orderCart.getTotal());
        List<OrderLine> orderLines = new ArrayList<>();
        for (Map.Entry<Long, OrderLine>entry : cart.entrySet()) {
            orderLines.add(entry.getValue());
        }
        paymentRequest.setOrderLines(orderLines);

        return paymentRequest;
    }


}
