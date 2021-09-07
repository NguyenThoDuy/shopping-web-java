package com.web.shopping.controller.admin;

import com.web.shopping.model.entity.Order;
import com.web.shopping.model.entity.User;
import com.web.shopping.service.OrderService;
import com.web.shopping.service.ProductService;
import com.web.shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class AdminController {
    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;
    @GetMapping("admin/index")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView  home(){
        int totalProduct = productService.getAll().size();
        int totalUser = userService.getAll().size();
       int totalOrder = orderService.getAll().size();
        ModelAndView mav = new ModelAndView("admin/index");
        mav.addObject("totalProduct",totalProduct);
        mav.addObject("totalUser",totalUser);
        mav.addObject("totalOrder",totalOrder);
        mav.addObject("products", productService.hotSaleProduct());
        return mav;
    }
}
