package com.web.shopping.dto;

import com.web.shopping.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLine implements Serializable {
    private Product product;
    private int count;
    public void increaseByOne() {
        count += 1;
    }
}
