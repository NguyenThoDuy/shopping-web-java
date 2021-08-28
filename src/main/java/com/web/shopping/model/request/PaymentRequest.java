package com.web.shopping.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private long user_id;
    private String username;
    private int phone;
    private String email;
    private String address;
}
