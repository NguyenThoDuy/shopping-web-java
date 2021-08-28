package com.web.shopping.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank(message = "name product is required")
    @Size(min = 3, max = 100, message = "Name product must between 3 and 100")
    private String name;
    @NotBlank(message = "manufacturer is required")
    private String manufacturer;
    private MultipartFile photo;
    private String list_image;
    private Double import_price;
    private Double sale_price;
    private int number_import;
    private int catalog_id;
    private String description;

}
