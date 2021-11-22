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
//    or p.catalog.name like concat('%', :keywork, '%')"
    @Query("select p from Product p where p.name like concat('%', :keywork, '%')")
    List<Product> search(String keywork);
}
