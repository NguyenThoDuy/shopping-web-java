package com.web.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class Cart {
    private List<OrderLine> orderLines;
    // tong tien sp
    private long rawTotal;
    //chiet khau
    private long discount;
    private long vatTax;
    private long total;

    public Cart() {
        orderLines = Collections.emptyList();
        rawTotal = 0;
        discount = 0;
        vatTax = 0;
        total = 0;
    }

    public Cart(List<OrderLine> orderLines, double discountPercentage, boolean isVATIncluded){
        this.orderLines = orderLines;
        rawTotal = 0;

        orderLines.stream().forEach(orderLine -> {
            rawTotal += orderLine.getCount() * orderLine.getProduct().getSale_price();
        });

        discount = (long) Math.round(rawTotal * discountPercentage);

        if (isVATIncluded) {
            vatTax = (long) Math.round((rawTotal - discount) * 0.01f);
        } else {
            vatTax = 0;
        }

        total = rawTotal - discount + vatTax;
    }
}
