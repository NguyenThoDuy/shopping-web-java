package com.web.shopping.model.request;

import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
    private long id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private List<Long> roles;
}
