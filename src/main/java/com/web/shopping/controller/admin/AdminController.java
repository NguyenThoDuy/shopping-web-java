package com.web.shopping.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class AdminController {

    @GetMapping("admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String home(){

        return "redirect:/product";
    }
}
