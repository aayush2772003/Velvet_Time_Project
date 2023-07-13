package com.oops.project.product.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.oops.project.product.domain.Product;
import com.oops.project.product.exception.BadResourceException;
import com.oops.project.product.exception.ResourceAlreadyExistsException;
import com.oops.project.product.exception.ResourceNotFoundException;
import com.oops.project.product.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    private boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    public Product findById(Long id) throws ResourceNotFoundException {
        Product product = productRepository.findById(id).orElse(null);
        if (product==null) {
            throw new ResourceNotFoundException("Cannot find Product with id: "+id);
        } else 
        return product;
    }

    public List<Product> findAll(int pageNumber, int rowPerPage) {
        List<Product> products = new ArrayList<>();
        Pageable sortedByIdAsc = PageRequest.of(pageNumber-1,rowPerPage,Sort.by("id").ascending());
        productRepository.findAll(sortedByIdAsc).forEach(products::add);
        return products;
    }
    
    public Product save(Product product) throws BadResourceException, ResourceAlreadyExistsException {
        if (StringUtils.hasLength(product.getName())) {
            if (product.getId() != null && existsById(product.getId())) { 
                throw new ResourceAlreadyExistsException("Product with id: " + product.getId() + " already exists");
            }
            return productRepository.save(product);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save product");
            exc.addErrorMessage("Product is null or empty");
            throw exc;
        }
    }
    
    public void update(Product product) 
            throws BadResourceException, ResourceNotFoundException {
        if (StringUtils.hasLength(product.getName())) {
            if (!existsById(product.getId())) {
                throw new ResourceNotFoundException("Cannot find Product with id: " + product.getId());
            }
            productRepository.save(product);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save product");
            exc.addErrorMessage("Product is null or empty");
            throw exc;
        }
    }
    
    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) { 
            throw new ResourceNotFoundException("Cannot find product with id: " + id);
        } else {
            productRepository.deleteById(id);
        }
    }
    
    public Long count() {
        return productRepository.count();
    }
}
