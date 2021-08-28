package com.web.shopping.controller.admin;


import com.web.shopping.model.entity.Product;
import com.web.shopping.model.request.ProductEditForm;
import com.web.shopping.model.request.ProductRequest;
import com.web.shopping.model.request.SearchRequest;
import com.web.shopping.service.CatalogService;
import com.web.shopping.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {
    private final CatalogService catalogService;
    private final ProductService productService;
//    private final CartService cartService;

    @GetMapping("")
    public String index(Model model, HttpServletRequest request
            , RedirectAttributes redirect) {
        request.getSession().setAttribute("products", null);
        if(model.asMap().get("success") != null)
            redirect.addFlashAttribute("success",model.asMap().get("success").toString());
        return "redirect:/product/page/1";
    }

    @GetMapping("/page/{pageNumber}")
    public String showProductPage(HttpServletRequest request,
                                   @PathVariable int pageNumber, Model model) {
        PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("products");
        int pagesize = 2;
        List<Product> list =(List<Product>) productService.getAll();
        if (pages == null) {
            pages = new PagedListHolder<>(list);
            pages.setPageSize(pagesize);
        } else {
            final int goToPage = pageNumber - 1;
            if (goToPage <= pages.getPageCount() && goToPage >= 0) {
                pages.setPage(goToPage);
            }
        }
        request.getSession().setAttribute("products", pages);
        int current = pages.getPage() + 1;
        int begin = Math.max(1, current - list.size());
        int end = Math.min(begin + 5, pages.getPageCount());
        int totalPageCount = pages.getPageCount();
        String baseUrl = "/product/page/";
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("products", pages);
        model.addAttribute("search", new SearchRequest());
        return "/admin/product/index";
    }


    //add
    @GetMapping ("add")
    public ModelAndView add(){
        ModelAndView mav = new ModelAndView("admin/product/add");
        mav.addObject("catalogs", catalogService.getAll());
        mav.addObject("product", new ProductRequest());
        return mav;
    }
    //add
     @PostMapping(value = "add", consumes = {"multipart/form-data"})
    public String upload(@Valid @ModelAttribute ProductRequest request, BindingResult result, Model model) {
        // xử lí các trường để trống thi báo lỗi

        productService.add(request);
        return "redirect:/product";
    }


    @GetMapping("detailcontrol/{id}")
    public ModelAndView showDetailProductControl(@Valid @PathVariable("id") long id, HttpSession session) {
        Product productById = productService.showById(id);
        ModelAndView mav = new ModelAndView("admin/product/product-detail-control");
        mav.addObject("product", productById);
        return mav;
    }
    //edit
    @GetMapping("edit/{id}")
    public ModelAndView edit(@Valid @PathVariable("id") long id) {
        Product productById = productService.showById(id);
        ModelAndView mav = new ModelAndView("admin/product/edit");
        mav.addObject("catalogs", catalogService.getAll());
        mav.addObject("product", productById);
        return mav;
    }

    @PostMapping("update")
    public String update(@Valid @ModelAttribute ProductEditForm request){
        productService.update(request);
        return "redirect:/product";
    }


    //delete product
    @GetMapping("delete/{id}")
    public String delete(@Valid @PathVariable("id") long id){
        productService.delete(id);
        return "redirect:/product";
    }

    //search
    @GetMapping("search")
    public ModelAndView search(@ModelAttribute SearchRequest searchRequest){
        System.out.println(searchRequest.getKeyword());
        List<Product> products = null;
        if (searchRequest.getKeyword().isEmpty()) {
            products = productService.getAll();
        }else {
            products = productService.search(searchRequest);
        }
        ModelAndView mav = new ModelAndView("product/list-control");
        mav.addObject("products", products);
        mav.addObject("search", new SearchRequest());
        return mav;
    }


}
