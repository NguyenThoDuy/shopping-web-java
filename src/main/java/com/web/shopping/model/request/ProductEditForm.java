package com.web.shopping.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEditForm {
    private long id;
    private String name;
    private String manufacturer;
    private MultipartFile image;
    private String list_image;
    private Double import_price;
    private Double sale_price;
    private int number_import;
    private int catalog;
    private String description;

}
