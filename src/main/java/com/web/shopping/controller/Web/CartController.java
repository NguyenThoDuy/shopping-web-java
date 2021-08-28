package com.web.shopping.controller.Web;

import com.web.shopping.dto.Cart;
import com.web.shopping.dto.OrderLine;
import com.web.shopping.model.entity.User;
import com.web.shopping.model.request.PaymentRequest;
import com.web.shopping.model.request.SearchRequest;
import com.web.shopping.service.CartService;
import com.web.shopping.service.CatalogService;
import com.web.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Properties;


@Controller
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {
//    @RequiredArgsConstructor
    private final CartService cartService;
    private final CatalogService catalogService;
    private final ProductService productService;
    private final JavaMailSender sender;

    @GetMapping("add/{id}")
    public String addCart(@PathVariable("id") Long id, HttpSession session, Model model, HttpServletRequest request, RedirectAttributes redirect){
        //tra lai dung cai link vua gui request
        cartService.addToCart(id, session);
        model.addAttribute("cartCount", cartService.countItemInCart(session));
        return "redirect:" + request.getHeader("Referer");
    }
    //addbycount
    @PostMapping("addbycount")
    public String addCartByQty(HttpSession session, Model model, HttpServletRequest request,
                               @ModelAttribute OrderLine orderLine,
                               @RequestParam("id") long id
                               ){
        System.out.println(orderLine.getCount());
        //tra lai dung cai link vua gui request
        cartService.addCartByCount(session, id, orderLine.getCount());
        model.addAttribute("cartCount", cartService.countItemInCart(session));
        return "redirect:" + request.getHeader("Referer");
    }

    //checkout
    @GetMapping("checkout")
    public ModelAndView checkout(HttpSession session) {
        long millis = System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        ModelAndView mav = new ModelAndView("web/cart/checkout");
        mav.addObject("cart", cartService.getCart(session));
        mav.addObject("cartCount", cartService.countItemInCart(session));
        mav.addObject("date", date);
        User user = (User) session.getAttribute("USER");
        mav.addObject("user", user);
        mav.addObject("catalogs", catalogService.getAll());
        return mav;
    }

    //remove item
    @GetMapping("remove/{id}")
    public String removeProducInCart( HttpSession session,@PathVariable("id") long id){
        System.out.println(id);
        HashMap<Long, Cart> cartItems = (HashMap<Long, Cart>) session.getAttribute("CART");
//        cartItems.size();
        if (cartItems == null) {
            cartItems = new HashMap<>();
        }
        if (cartItems.containsKey(id)) {
            cartItems.remove(id);
        }
        cartItems.size();
        session.setAttribute("CART", cartItems);
        return "redirect:/cart/checkout";
    }

    //remove cart
    @GetMapping("remove")
    public String removeCart(HttpSession session){
      session.removeAttribute("CART");
        return "redirect:/cart/checkout";
    }
    //add count product
    @GetMapping("addCount/{id}")
    public String addCount (HttpSession session, HttpServletRequest request, @PathVariable("id") long id){
        cartService.addCount(session, id);
        return "redirect:" +  request.getHeader("Referer");
    }
    //remove count product
    @GetMapping("removeCount/{id}")
    public String removeCount (HttpSession session, HttpServletRequest request, @PathVariable("id") long id){
        cartService.removeCount(session, id);
        return "redirect:" +  request.getHeader("Referer");
    }
    @GetMapping("error")
    public ModelAndView error(){
        ModelAndView mav =new ModelAndView("client/error/404error");
        return mav;
    }

    @GetMapping("payment")
    public ModelAndView payment(HttpSession session){
        ModelAndView mav =new ModelAndView("web/cart/payment");
        long millis = System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        User user = (User) session.getAttribute("USER");
        mav.addObject("user", user);
        mav.addObject("cart", cartService.getCart(session));
        mav.addObject("payment", new PaymentRequest());
        mav.addObject("cartCount", cartService.countItemInCart(session));
        mav.addObject("date", date);
        mav.addObject("catalogs", catalogService.getAll());
        return mav;
    }
}
