package com.web.shopping.controller.admin;

import com.web.shopping.model.entity.User;
import com.web.shopping.model.request.UserRequest;
import com.web.shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("login")
    public ModelAndView login(@PathParam("error") String error){
        ModelAndView mav = new ModelAndView("auth/login");
        mav.addObject("error", error);
        return mav;
    }
    @GetMapping("sigin")
    public ModelAndView sigup(){
        ModelAndView mav = new ModelAndView("auth/login");
        return mav;
    }

    //add
    @GetMapping("sigup")
    public ModelAndView add(){
        ModelAndView mav = new ModelAndView("auth/add");
        mav.addObject("user", new UserRequest());
        return mav;
    }

    @PostMapping("sigup")
    public ModelAndView add(@ModelAttribute UserRequest user) throws InterruptedException {
        Boolean check = userService.add(user);
        ModelAndView mav  = new ModelAndView();

        if(check){
            mav.addObject("error", "Tạo tài khoản không thành công");
            Thread.sleep(1000);
            mav.setViewName("redirect:/auth/login");
        }else {
            mav.setViewName("auth/sigup");
            mav.addObject("user", new UserRequest());
            mav.addObject("error", "Tạo tài khoản không thành công");
        }

        return mav;
    }

    @GetMapping("edit/{id}")
    public ModelAndView showById(@PathVariable Long id){

        User userById = userService.findById(id);
        ModelAndView mav  = new ModelAndView("auth/edit");
        mav.addObject("user",userById);
        return mav;
    }

    @PostMapping("update")
    public ModelAndView update(@ModelAttribute UserRequest userRequest, Model model) throws InterruptedException {
        System.out.println("========> ID* " + userRequest.getId());
        boolean checkUpdate = userService.update(userRequest);
        ModelAndView mav  = new ModelAndView();
         if(checkUpdate){
             mav.setViewName("redirect:/");
         }else {
             User userById = userService.findById(userRequest.getId());
             mav.addObject("user",userById);
             mav.addObject("error", "chinh sua khong thanh cong");
             mav.setViewName("auth/edit");
         }
        return mav;
    }

}
