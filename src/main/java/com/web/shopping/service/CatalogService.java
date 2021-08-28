package com.web.shopping.service;

import com.web.shopping.model.entity.Catalog;
import com.web.shopping.model.request.CatalogRequest;
import com.web.shopping.model.request.SearchRequest;

import java.util.List;

public interface CatalogService {
    List<Catalog> getAll();

    Catalog add(CatalogRequest request);

    Catalog showById(long id);

    Catalog update(CatalogRequest request);

    boolean delete(long id);

    List<Catalog> search(SearchRequest request);
}
