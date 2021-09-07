package com.web.shopping.controller.admin;

import com.web.shopping.model.entity.Order;
import com.web.shopping.model.entity.OrderDetail;
import com.web.shopping.model.entity.User;
import com.web.shopping.model.request.HandleRequest;
import com.web.shopping.model.request.PaymentRequest;
import com.web.shopping.model.request.Statusrequest;
import com.web.shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriBuilder;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    public List<HandleRequest> showListStatus(){
        List<HandleRequest> handleRequests = new ArrayList<>();
        handleRequests.add(new HandleRequest(0, "Chưa Xử Lí"));
        handleRequests.add(new HandleRequest(1, "Xác Nhận Đơn Hàng"));
        handleRequests.add(new HandleRequest(2, "Đang Vận Chuyển"));
        handleRequests.add(new HandleRequest(3, "Đã Giao Hàng"));
        return handleRequests;
    }
    @PostMapping("order/payment")
    public ModelAndView payment(@ModelAttribute PaymentRequest request, HttpSession session) {
        System.out.println("======================>" + request.getUser_id());
        Order order = orderService.save(request, session);
        int codeOrder = (int) order.getId();
        ModelAndView mav = new ModelAndView("web/error/ordersuccess");
        mav.addObject("code", codeOrder);
        mav.addObject("handle", new Statusrequest());
        return mav;
    }

    //order index
    @GetMapping("admin/order/index")
    public ModelAndView home(
            @PageableDefault(page = 0, size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.DESC)
            }) Pageable pageable) {
        ModelAndView mav = new ModelAndView("admin/order/index");
        Optional<Page<Order>> page = orderService.list(pageable);
        mav.addObject("page", page.get());
        mav.addObject("currentPage", page.get().getNumber());
        mav.addObject("previous", page.get().hasPrevious());
        mav.addObject("handle", new Statusrequest());
        mav.addObject("statuss", showListStatus());
        if (page.get().hasPrevious())
            mav.addObject("previousPage", page.get().getNumber() - 1);
        mav.addObject("next", page.get().hasNext());
        if (page.get().hasNext()) {
            mav.addObject("nextPage", page.get().getNumber() + 1);
        }
        return mav;
    }

    //order detail
    @GetMapping("/admin/order/detail/{id}")
    public ModelAndView orderDetail(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView();
        String ids = String.valueOf(id);
        Order order = orderService.searchOrderById(ids);
        if (order != null) {
            mav.setViewName("admin/order/detail");
            List<OrderDetail> orderDetails = orderService.findOrderId(ids);
            mav.addObject("order", order);
            mav.addObject("orderDetails", orderDetails);
        }
        return mav;
    }

    //handle
    @GetMapping("admin/order/handle/{id}")
    public ModelAndView handle(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView();
        String ids = String.valueOf(id);
        Order order = orderService.searchOrderById(ids);

        if (order != null) {
            mav.setViewName("admin/order/handle");
            List<OrderDetail> orderDetails = orderService.findOrderId(ids);
            mav.addObject("order", order);
            mav.addObject("orderDetails", orderDetails);
            mav.addObject("handle", new Statusrequest());
            mav.addObject("statuss", showListStatus());
        }

        return mav;
    }

    //handle
    @PostMapping("admin/order/handle/update")
    public String handleUpdate(@Param("id") Long id, @ModelAttribute Statusrequest request) {
        orderService.updateStatusOrder(id, request.getStatus());
        return "redirect:/admin/order/handle/" + request.getId();
    }

    //search order by status
    @GetMapping("admin/index/orderStatus")
    public ModelAndView searchByOrderStatus(@ModelAttribute Statusrequest request) {
        ModelAndView mav = new ModelAndView("admin/order/findByOrderStatus");
        List<Order> orders = orderService.searchByOrderStatus(request.getStatus());
        mav.addObject("handle", new Statusrequest());
        mav.addObject("statuss", showListStatus());
        mav.addObject("orders", orders);
        return mav;
    }

}
