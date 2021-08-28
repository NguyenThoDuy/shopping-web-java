package com.web.shopping.service;

import com.web.shopping.model.entity.Role;
import com.web.shopping.model.request.RoleRequest;

import java.util.List;

public interface RoleService {
    void add(RoleRequest request);

    List<Role> getAll();
}
