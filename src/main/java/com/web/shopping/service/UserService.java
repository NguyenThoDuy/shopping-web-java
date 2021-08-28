package com.web.shopping.service;


import com.web.shopping.model.entity.User;
import com.web.shopping.model.request.UserRequest;

public interface UserService {
    Boolean add(UserRequest user);

    User findById(Long id);

    boolean update(UserRequest userRequest);
}
