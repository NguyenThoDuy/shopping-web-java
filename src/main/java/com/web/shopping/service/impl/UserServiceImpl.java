package com.web.shopping.service.impl;

import com.web.shopping.Repository.RoleRepo;
import com.web.shopping.Repository.UserRepo;
import com.web.shopping.model.entity.Product;
import com.web.shopping.model.entity.Role;
import com.web.shopping.model.entity.User;
import com.web.shopping.model.request.UserRequest;
import com.web.shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final  RoleRepo roleRepo;
    private final HttpSession session;

    private final PasswordEncoder passwordEncoder;
    long millis = System.currentTimeMillis();
     public java.sql.Date date=new java.sql.Date(millis);

     //defaul add user
    @Override
    public Boolean add(UserRequest user) {
        Boolean check;
        User userCreated = null;
        try {
            Role role = roleRepo.getById(2L);
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            User person = new User();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
                person.setUsername(user.getUsername());
                person.setPassword(encodedPassword);
                person.setEmail(user.getEmail());
                person.setPhone(user.getPhone());
                person.setCreate_at(date);
                person.setRoles(roles);
                //
                userCreated = userRepo.save(person);
                if(user != null){
                    check = true;
                }else {
                    check = false;
                }
        } catch (Exception ex) {
            log.info(ex.getMessage());
            check = false;
        }
        return check;
    }

    @Override
    public User findById(Long id) {
        User userById = null;
        try {
            Optional<User> user = userRepo.findById(id);
            if(user != null){
                userById =user.get();
            }
        }catch (Exception ex){
            log.info("user fail");
        }
        return userById;
    }

    @Override
    public boolean update(UserRequest userRequest) {
        Boolean check = false;
        User userUpdate = null;
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        Optional<User> userCreated = userRepo.findById(userRequest.getId());
        try {
            if(userCreated != null){
                User user = userCreated.get();
                user.setUsername(userRequest.getUsername());
                user.setEmail(userRequest.getEmail());
                user.setPassword(encodedPassword);
                user.setPhone(userRequest.getPhone());
                user.setCreate_at(userCreated.get().getCreate_at());
                //
                userUpdate = userRepo.save(user);
                if(userUpdate != null){
                    check = true;
                    session.setAttribute("USER", userUpdate);
                }else {
                   check = false;
                }
            }
        }catch (Exception ex){
            log.info("update detail");
            check = false;
        }
        return check;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepo.findAll();
        return users;
    }

    @Override
    public Optional<Page<User>> list(Pageable pageable) {
        Page<User> page = null;
        try {
            page = userRepo.findAll(pageable);
        } catch (Exception ex) {
            log.error("get all car error: " + ex.getMessage());
        }
        return Optional.ofNullable(page);
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}
