package com.web.shopping.service.impl;

import com.web.shopping.Repository.CatalogRepo;
import com.web.shopping.exception.ResourceNotFoundException;
import com.web.shopping.model.entity.Catalog;
import com.web.shopping.model.request.CatalogRequest;
import com.web.shopping.model.request.SearchRequest;
import com.web.shopping.service.CatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {
    private final CatalogRepo catalogRepo;
    @Override
    public List<Catalog> getAll() {
        List<Catalog> catalogs = null;
        try {
            catalogs = catalogRepo.findAll();
        }catch (Exception ex){
            log.info(ex.getMessage());
        }
        return catalogs;
    }

    @Override
    public Catalog add(CatalogRequest request) {
        Catalog catalogCreate = null;
        try {
            Catalog newCatalog = new Catalog();
            newCatalog.setName(request.getName().toUpperCase());
            //
            catalogCreate = catalogRepo.save(newCatalog);
        }catch (Exception ex){
            log.info(ex.getMessage());
        }
        return catalogCreate;
    }

    @Override
    public Catalog showById(long id) {
        Catalog catalogById = catalogRepo.getById(id);
        return catalogById;
    }

    @Override
    public Catalog update(CatalogRequest request) {
        return catalogRepo.findById(request.getId()).map(post -> {
            post.setName(request.getName().toUpperCase());
            return catalogRepo.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + request.getId() + " not found"));
    }

    @Override
    public boolean delete(long id) {
        return catalogRepo.findById(id).map(post -> {
            catalogRepo.delete(post);
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException("catalog by id " + id + " not found"));
    }

    @Override
    public List<Catalog> search(SearchRequest request) {
        List<Catalog> catalogList = getAll();
        return catalogList.stream()
                .filter(p -> p.matchWithKeyword(request.getKeyword()))
                .collect(Collectors.toList());

    }
}
