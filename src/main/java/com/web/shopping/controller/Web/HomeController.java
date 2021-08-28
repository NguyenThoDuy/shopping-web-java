package com.web.shopping.controller.Web;

import com.web.shopping.model.entity.Product;
import com.web.shopping.model.entity.User;
import com.web.shopping.service.CatalogService;
import com.web.shopping.service.ProductService;
import com.web.shopping.service.impl.CartServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final CatalogService catalogService;
    private final ProductService productService;
    private final CartServiceImpl cartService;


    @GetMapping("")
    public ModelAndView list(
            @PageableDefault(page = 0, size = 8)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.DESC)
            }) Pageable pageable, HttpSession session) {
        User user = (User) session.getAttribute("USER");
        log.info("page: " + pageable.toString());
        ModelAndView mav = new ModelAndView("web/home/main-layout");
        Optional<Page<Product>> page = productService.list(pageable);
        mav.addObject("page", page.get());
        mav.addObject("currentPage", page.get().getNumber());
        mav.addObject("previous", page.get().hasPrevious());
        mav.addObject("catalogs", catalogService.getAll());
        mav.addObject("user", user);
        mav.addObject("cartCount", cartService.countItemInCart(session));
        if (page.get().hasPrevious())
            mav.addObject("previousPage", page.get().getNumber() - 1);
        mav.addObject("next", page.get().hasNext());
        if (page.get().hasNext()) {
            mav.addObject("nextPage", page.get().getNumber() + 1);
        }
        return mav;
    }

    //    @GetMapping("")
//    public ModelAndView home(HttpSession session){
//        User user = (User) session.getAttribute("USER");
//        ModelAndView mav = new ModelAndView("web/index");
//        mav.addObject("catalogs", catalogService.getAll());
//        mav.addObject("user", user);
//        return mav;
//    }
    @GetMapping("productdetail/{id}")
    public ModelAndView showDetailProductControl(@Valid @PathVariable("id") long id, HttpSession session) {
        Product productById = productService.showById(id);
        ModelAndView mav = new ModelAndView("web/product/product-detail");
        mav.addObject("product", productById);
        User user = (User) session.getAttribute("USER");
        mav.addObject("user", user);
        mav.addObject("cartCount", cartService.countItemInCart(session));
        mav.addObject("catalogs", catalogService.getAll());
        return mav;
    }
}

