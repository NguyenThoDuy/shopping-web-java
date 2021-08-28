package com.web.shopping.model.request;

import com.web.shopping.dto.OrderLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private long user_id;
    private String name;
    private int phone;
    private String email;
    private String address;
    List<OrderLine> orderLines;
    private double totalPrice;
}
