package com.web.shopping.Repository;

import com.web.shopping.model.entity.Catalog;
import com.web.shopping.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepo extends JpaRepository<Catalog, Long> {
    @Query(value = "SELECT p FROM Product p WHERE p.catalog.id = :catalogId", nativeQuery = true)
    List<Product> findProductsByCatalogId(String catalogId);
}
