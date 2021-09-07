package com.web.shopping.controller.admin;

import com.web.shopping.model.entity.Product;
import com.web.shopping.model.entity.User;
import com.web.shopping.model.request.UserRequest;
import com.web.shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("admin/user/index")
    public ModelAndView index(
        @PageableDefault(page = 0, size = 10)
        @SortDefault.SortDefaults({
                @SortDefault(sort = "id", direction = Sort.Direction.DESC)
            }) Pageable pageable) {
            ModelAndView mav = new ModelAndView("admin/user/index");
            Optional<Page<User>> page = userService.list(pageable);
            mav.addObject("page", page.get());
            mav.addObject("currentPage", page.get().getNumber());
            mav.addObject("previous", page.get().hasPrevious());
            if (page.get().hasPrevious())
                mav.addObject("previousPage", page.get().getNumber() - 1);
            mav.addObject("next", page.get().hasNext());
            if (page.get().hasNext()) {
                mav.addObject("nextPage", page.get().getNumber() + 1);
            }
            return mav;
    }
    //add new user
    @GetMapping("/admin/user/add")
    public ModelAndView add(){
        ModelAndView mav = new ModelAndView("admin/user/index");
        mav.addObject("user", new UserRequest());
        mav.addObject("code", 1);
        return mav;
    }
    @PostMapping("/admin/user/add")
    public String addNew(@ModelAttribute UserRequest user){
        userService.add(user);
        return "redirect:/admin/user/index";
    }
    //delete
    @GetMapping("/admin/user/delete/{id}")
    public String delete(@PathVariable Long id){
         userService.delete(id);
         return "redirect:/admin/user/index";
    }
}
