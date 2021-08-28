package com.web.shopping.model.response;

import com.web.shopping.dto.OrderLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String name;
    private int phone;
    private String Email;
    private String address;
    private double total;
    private List<OrderLine> orderLines;
}
