package com.web.shopping.service.impl;

import com.web.shopping.Repository.UserRepo;
import com.web.shopping.model.entity.Role;
import com.web.shopping.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        log.info("==> username: {}", user.getUsername());
        log.info("==> role: " + user.getRoles().get(0).getName());
        if (user == null) {

        }
        session.setAttribute("USER", user);
        List<GrantedAuthority> grantList = new ArrayList<>();
        for (Role role : user.getRoles()) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
            grantList.add(authority);
        }

        UserDetails userDetail = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantList);
        return userDetail;
    }
}
