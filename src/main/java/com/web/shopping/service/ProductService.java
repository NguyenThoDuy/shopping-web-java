package com.web.shopping.service;


import com.web.shopping.model.entity.Catalog;
import com.web.shopping.model.entity.Product;
import com.web.shopping.model.request.ProductEditForm;
import com.web.shopping.model.request.ProductRequest;
import com.web.shopping.model.request.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product add(ProductRequest request);

    List<Product> getAll();

    Product showById(long id);

    Product update(ProductEditForm request);

    Boolean delete(long id);

    List<Product> search(String searchRequest);

    Optional<Page<Product>> list(Pageable pageable);

    List<Product> findByCatalog(Long id);

    List<Product> hotSaleProduct();

    List<Product> filterPrice(String price);

    List<Product> filterByPriceAndCatalog(Long catalog_id, String price);

    Optional<Page<Product>> sort( int key);
}
