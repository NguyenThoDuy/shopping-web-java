package com.web.shopping.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "image")
    private String image;
    @Column(name = "list_image")
    private String list_image;
    @Column(name = "import_price")
    private Double import_price;
    @Column(name = "sale_price")
    private Double sale_price;
    @Column(name = "number_import")
    private int number_import;
    @Column(name = "number_out")
    private int number_out;
    @Column(name = "created_at")
    private Date created_at;
    @Column(name = "update_at")
    private Date update_at;
    @Column(name = "view")
    private int view;
    @Column(name = "description", length = 1024)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id") // thông qua khóa ngoại catalog_id
    private Catalog catalog;

    @Transient
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    // MapopedBy trỏ tới tên biến Address ở trong Person.
    private List<OrderDetail> carts;

    //
    public boolean matchWithKeyword(String keyword) {
        String keywordLowerCase = keyword.toLowerCase();
        return (name.toLowerCase().contains(keywordLowerCase) ||
                manufacturer.toLowerCase().contains(keywordLowerCase));
    }
}
