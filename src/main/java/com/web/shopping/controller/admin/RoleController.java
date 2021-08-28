package com.web.shopping.controller.admin;

import com.web.shopping.model.entity.Role;
import com.web.shopping.model.request.RoleRequest;
import com.web.shopping.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("add")
    public String add(RoleRequest request) {
        roleService.add(request);
        return "ok";
    }

    @GetMapping("show")
    public List<Role> list(){
        List<Role> roles = roleService.getAll();

        return roles;
    }
}
