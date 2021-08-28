package com.web.shopping.controller.admin;

import com.web.shopping.model.entity.Order;
import com.web.shopping.model.entity.OrderDetail;
import com.web.shopping.model.request.PaymentRequest;
import com.web.shopping.model.response.OrderResponse;
import com.web.shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("payment")
    public ModelAndView payment(@ModelAttribute PaymentRequest request, HttpSession session){
        System.out.println("======================>"+request.getUser_id());
        Order order = orderService.save(request, session);
        int codeOrder = (int) order.getId();
        ModelAndView mav = new ModelAndView("web/error/ordersuccess");
        mav.addObject("code", codeOrder);
        return mav;
    }

    @GetMapping("search/{id}")
    public OrderResponse show(@PathVariable Long id){
        OrderResponse orderDetail = orderService.findByOrder(id);

        return orderDetail;
    }
}
