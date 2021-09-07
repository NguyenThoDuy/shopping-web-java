package com.web.shopping.service.impl;

import com.web.shopping.Repository.*;
import com.web.shopping.dto.Cart;
import com.web.shopping.dto.OrderLine;
import com.web.shopping.model.entity.Order;
import com.web.shopping.model.entity.OrderDetail;
import com.web.shopping.model.entity.Product;
import com.web.shopping.model.entity.User;
import com.web.shopping.model.request.OrderRequest;
import com.web.shopping.model.request.PaymentRequest;
import com.web.shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final OrderDetailRepo orderDetailRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final ProductServiceImpl productImpl;
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
    public Order searchOrderById(String id) {
       Order order = null;
       try {
           order = orderRepo.getById(Long.valueOf(id));

       }catch (Exception ex){
           log.info(ex.getMessage());
       }

       return order;
    }

    @Override
    public List<OrderDetail> findOrderId(String id) {
        int ids = Integer.parseInt(id);
        List<OrderDetail> orderDetails = orderDetailRepo.findAll().stream()
                .filter(p -> p.getOrder().getId() == ids).collect(Collectors.toList());

        return orderDetails;
    }

    //get ALL
    @Override
    public List<Order> getAll() {
        List<Order> orders = orderRepo.findAll();
        return orders;
    }

    @Override
    public Optional<Page<Order>> list(Pageable pageable) {
        Page<Order> page = null;
        try {
            page = orderRepo.findAll(pageable);
        } catch (Exception ex) {
            log.error("get all car error: " + ex.getMessage());
        }
        return Optional.ofNullable(page);
    }

    @Override
    public void updateStatusOrder(Long id, int status) {
        Optional<Order> order = orderRepo.findById(id);
        if(order.isPresent()){
            order.get().setStatus(status);
            orderRepo.save(order.get());
        }
    }

    @Override
    public List<Order> searchByOrderStatus(int status) {
        List<Order> orders = getAll().stream()
                .filter(o -> o.getStatus() == status)
                .collect(Collectors.toList());
        return orders;
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
