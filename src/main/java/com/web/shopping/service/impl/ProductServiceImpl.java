package com.web.shopping.service.impl;


import com.web.shopping.Repository.CatalogRepo;
import com.web.shopping.Repository.ProductRepo;
import com.web.shopping.exception.ResourceNotFoundException;
import com.web.shopping.model.entity.Catalog;
import com.web.shopping.model.entity.Product;
import com.web.shopping.model.request.ProductEditForm;
import com.web.shopping.model.request.ProductRequest;
import com.web.shopping.model.request.SearchRequest;
import com.web.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final CatalogRepo catalogRepo;
    private final FileService fileService;
    long millis = System.currentTimeMillis();
    public java.sql.Date date = new java.sql.Date(millis);

    //getAll
    @Override
    public List<Product> getAll() {
        List<Product> productList = null;
        try {
            productList = productRepo.findAll();
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return productList;
    }

    //add
    @Override
    public Product add(ProductRequest request) {
        System.out.println(request.toString());
        Product productCreate = null;
        Optional<Catalog> catalog = catalogRepo.findById((long) request.getCatalog_id());
        try {
            Product product = new Product();
            product.setName(request.getName());
            product.setManufacturer(request.getManufacturer());
            product.setImage(fileService.uploadFile(request.getPhoto()));
            product.setList_image(request.getList_image());
            product.setImport_price(request.getImport_price());
            product.setSale_price(request.getSale_price());
            product.setNumber_import(request.getNumber_import());
            product.setDescription(request.getDescription());
            product.setCreated_at(date);
            product.setCatalog(catalog.get());
            //save database
            productCreate = productRepo.save(product);
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return productCreate;
    }

    //showByid
    @Override
    public Product showById(long id) {
        Product productById = productRepo.getById(id);
        if (productById != null) {
            productById.setView(productById.getView() + 1);
            productRepo.save(productById);
//           System.out.println(productById.getView());
        }
        return productById;
    }

    //update
    @Override
    public Product update(ProductEditForm request) {
        log.info(" -------------->id:  " + request.getId());
        Product productEdit = null;
        Catalog catalog = catalogRepo.getById((long) request.getCatalog());
        Optional<Product> productCreated = productRepo.findById(request.getId());
        try {
            if (productCreated.isPresent()) {
                Product productUpdate = productCreated.get();
                productUpdate.setName(request.getName());
                productUpdate.setManufacturer(request.getManufacturer());
                productUpdate.setImage(fileService.uploadFile(request.getImage()));
                productUpdate.setImport_price(request.getImport_price());
                productUpdate.setSale_price(request.getSale_price());
                productUpdate.setNumber_import(request.getNumber_import());
                productUpdate.setDescription(request.getDescription());
                productUpdate.setUpdate_at(date);
                productUpdate.setCatalog(catalog);
                productEdit = productRepo.save(productUpdate);
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return productEdit;
    }

    @Override
    public Boolean delete(long id) {
        return productRepo.findById(id).map(post -> {
            productRepo.delete(post);
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException("product by id " + id + "not found"));
    }




    @Override
    public Optional<Page<Product>> list(Pageable pageable) {
        Page<Product> page = null;
        try {
            page = productRepo.findAll(pageable);
        } catch (Exception ex) {
            log.error("get all car error: " + ex.getMessage());
        }
        return Optional.ofNullable(page);
    }

    @Override
    public List<Product> findByCatalog(Long id) {
        List<Product> products = null;
        try {
            products = getAll().stream()
                    .filter(p -> p.getCatalog().getId() == id).collect(Collectors.toList());
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        Product product = new Product();

        return products;
    }

    @Override
    public List<Product> hotSaleProduct() {
        List<Product> products = getAll().stream()
                .sorted(Comparator.comparingInt(Product::getNumber_out).reversed())
                .limit(6)
                .collect(Collectors.toList());

        return products;
    }

    @Override
    public List<Product> filterPrice(String price) {
        int choose = Integer.parseInt(price);
        List<Product> products = null;
        switch (choose) {
            case 1:
                products = getAll().stream()
                        .filter(p -> p.getSale_price() <= 2000000)
                        .collect(Collectors.toList());
                break;
            case 2:
                products = getAll().stream()
                        .filter(p -> p.getSale_price() >= 2000000 && p.getSale_price() <= 5000000)
                        .collect(Collectors.toList());
                break;
            case 3:
                products = getAll().stream()
                        .filter(p -> p.getSale_price() >= 5000000 && p.getSale_price() <= 10000000)
                        .collect(Collectors.toList());
                break;
            case 4:
                products = getAll().stream()
                        .filter(p -> p.getSale_price() >= 10000000)
                        .collect(Collectors.toList());
                break;
        }
        return products;
    }

    @Override
    public List<Product> filterByPriceAndCatalog(Long catalog_id, String price) {
        List<Product> products = filterPrice(price).stream()
                .filter(p -> p.getCatalog().getId() == catalog_id)
                .collect(Collectors.toList());

        return products;
    }

    @Override
    public Optional<Page<Product>> sort(int key) {
        Page<Product> page = null;
        switch (key) {
            case 1:
                Pageable pageab1 = PageRequest.of(0, 12, Sort.by("name").ascending());
                page = productRepo.findAll(pageab1);
                break;
            case 2:
                Pageable pageab2 = PageRequest.of(0, 12, Sort.by("name").descending());
                page = productRepo.findAll(pageab2);
                break;
            case 3:
                Pageable pageab5 = PageRequest.of(0, 12, Sort.by("view").descending());
                page = productRepo.findAll(pageab5);
                break;
        }

        return Optional.ofNullable(page);
    }

    @Override
    public List<Product> searchByKyework(String keywork) {
        List<Product> products = productRepo.search(keywork);
        return products;
    }


//    @Override
//    public List<Product> searchByCatalog(SearchRequest request) {
//        Catalog catalogbySearch = catalogRepo.findByName(request.getKeyword().toUpperCase());
//        return getAll().stream()
//                .filter(p -> p.getCatalog().equals(catalogbySearch.getId()))
//                .collect(Collectors.toList());
//    }

}
