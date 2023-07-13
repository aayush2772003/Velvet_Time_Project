package com.oops.project.product.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oops.project.product.domain.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product,Long>, JpaSpecificationExecutor<Product>, CrudRepository<Product,Long> {
    
}
