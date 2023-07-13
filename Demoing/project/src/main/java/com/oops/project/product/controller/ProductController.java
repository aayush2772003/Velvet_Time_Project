package com.oops.project.product.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oops.project.product.domain.Product;
import com.oops.project.product.exception.ResourceNotFoundException;
import com.oops.project.product.service.ProductService;

@Controller
public class ProductController {
 
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
 
    private final int ROW_PER_PAGE = 5;
 
    @Autowired
    private ProductService productService;
 
    @Value("${msg.title}")
    private String title;
 
    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title", title);
        return "index";
    }
 
    @GetMapping(value = "/products")
    public String getProducts(Model model, @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<Product> products = productService.findAll(pageNumber, ROW_PER_PAGE);

        long count = productService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("products", products);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "product-list";
    }
 
    @GetMapping(value = "/products/{productId}")
    public String getProductById(Model model, @PathVariable long productId) {
        Product product = null;
        try {
            product = productService.findById(productId);
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", "Product not found");
        }
        model.addAttribute("product", product);
        return "product";
    }
 
    @GetMapping(value = {"/products/add"})
    public String showAddProduct(Model model) {
        Product product = new Product();
        model.addAttribute("add", true);
        model.addAttribute("product", product);

        return "product-edit";
    }
 
    @PostMapping(value = "/products/add")
    public String addProduct(Model model,@ModelAttribute("product") Product product) {
        try {
            Product newProduct = productService.save(product);
            return "redirect:/products/" + String.valueOf(newProduct.getId());
        } catch (Exception ex) {
            // log exception first, 
            // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
     
            //model.addAttribute("product", product);
            model.addAttribute("add", true);
            return "product-edit";
        }
    }
 
    @GetMapping(value = {"/products/{productId}/edit"})
    public String showEditProduct(Model model, @PathVariable long productId) {
        Product product = null;
        try {
            product = productService.findById(productId);
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", "Product not found");
        }
        model.addAttribute("add", false);
        model.addAttribute("product", product);
        return "product-edit";
    }
    
    @PostMapping(value = {"/products/{productId}/edit"})
    public String updateProduct(Model model,@PathVariable long productId,@ModelAttribute("product") Product product) {
        try {
            product.setId(productId);
            productService.update(product);
            return "redirect:/products/" + String.valueOf(product.getId());
        } catch (Exception ex) {
            // log exception first, 
            // then show error
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            return "product-edit";
        }
    }
    
    @GetMapping(value = {"/products/{productId}/delete"})
    public String showDeleteProductById(Model model, @PathVariable long productId) {
        Product product = null;
        try {
            product = productService.findById(productId);
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", "Product not found");
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("product", product);
        return "product";
    }
    
    @PostMapping(value = {"/products/{productId}/delete"})
    public String deleteProductById(Model model, @PathVariable long productId) {
        try {
            productService.deleteById(productId);
            return "redirect:/products";
        } catch (ResourceNotFoundException ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "product";
        }
    }
}