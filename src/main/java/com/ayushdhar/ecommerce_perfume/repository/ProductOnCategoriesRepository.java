package com.ayushdhar.ecommerce_perfume.repository;

import com.ayushdhar.ecommerce_perfume.entity.ProductOnCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOnCategoriesRepository extends JpaRepository<ProductOnCategories, String> {

}
