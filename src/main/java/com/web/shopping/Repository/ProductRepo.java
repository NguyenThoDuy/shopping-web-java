package com.web.shopping.Repository;

import com.web.shopping.model.entity.Product;

import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.From;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
//    @Query(value = "SELECT p FROM Product p WHERE p.catalog.id = :catalogId", nativeQuery = true)
//    List<Product> findProductsByCatalogId(String catalogId);

 @Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%', :keyword, '%') OR p.catalog.name LIKE CONCAT('%', :keyword, '%')")
    List<Product> search(String keywork);
}
