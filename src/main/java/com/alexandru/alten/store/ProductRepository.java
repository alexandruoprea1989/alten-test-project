package com.alexandru.alten.store;

import com.alexandru.alten.model.db.Category;
import com.alexandru.alten.model.db.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategories(List<Category> category);
}
