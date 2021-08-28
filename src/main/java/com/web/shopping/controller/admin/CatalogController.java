package com.web.shopping.controller.admin;

import com.web.shopping.model.entity.Catalog;
import com.web.shopping.model.request.CatalogRequest;
import com.web.shopping.model.request.SearchRequest;
import com.web.shopping.service.CatalogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    //show list
    @GetMapping("")
    public ModelAndView main() {
        List<Catalog> catalogs = catalogService.getAll();
        ModelAndView mav = new ModelAndView("admin/catalog/index");
        mav.addObject("catalogs", catalogs);
        mav.addObject("catalog", new CatalogRequest());
        mav.addObject("search", new SearchRequest());
        return mav;
    }

    //add
    @PostMapping("add")
    public String add(@Valid @ModelAttribute CatalogRequest request) {
        System.out.println(request.getName());
        catalogService.add(request);
        return "redirect:/catalog";
    }

    //edit
    @GetMapping("update/{id}")
    public ModelAndView showById(@Valid @PathVariable long id) {
        Catalog catalog = catalogService.showById(id);
        ModelAndView mav = new ModelAndView("admin/catalog/edit");
        mav.addObject("catalog", catalog);
        return mav;
    }

    @PostMapping("update")
    public String update(@Valid @ModelAttribute CatalogRequest request) {
        catalogService.update(request);
        return "redirect:/catalog";

    }

    //remove
    @GetMapping("delete/{id}")
    public String remove(@Valid @PathVariable long id) {
        catalogService.delete(id);
        return "redirect:/catalog";
    }

    //search
    @GetMapping("search")
    public ModelAndView search(@ModelAttribute SearchRequest searchRequest) {
        List<Catalog> catalogs = null;
        if (searchRequest.getKeyword().isEmpty()) {
            catalogs = catalogService.getAll();
        }else {
            catalogs = catalogService.search(searchRequest);
        }
        ModelAndView mav = new ModelAndView("admin/catalog/index");
        mav.addObject("catalogs", catalogs);
        mav.addObject("catalog", new CatalogRequest());
        mav.addObject("search", new SearchRequest());
        return mav;
    }
}
