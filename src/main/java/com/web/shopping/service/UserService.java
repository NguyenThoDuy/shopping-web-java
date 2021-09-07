package com.web.shopping.service;


import com.web.shopping.model.entity.User;
import com.web.shopping.model.request.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Boolean add(UserRequest user);

    User findById(Long id);

    boolean update(UserRequest userRequest);

    List<User> getAll();

    Optional<Page<User>> list(Pageable pageable);

    void delete(Long id);

}
