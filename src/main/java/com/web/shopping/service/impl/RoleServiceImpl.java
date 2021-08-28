package com.web.shopping.service.impl;

import com.web.shopping.Repository.RoleRepo;
import com.web.shopping.model.entity.Role;
import com.web.shopping.model.request.RoleRequest;
import com.web.shopping.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;


    @Override
    public void add(RoleRequest request) {
        Role role = new Role();
        role.setName(request.getName());
        role.setParent(request.getParent());
       Role role1 =  roleRepo.save(role);
        System.out.println(role1);
    }
}
