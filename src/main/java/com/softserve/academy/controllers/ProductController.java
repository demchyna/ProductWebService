package com.softserve.academy.controllers;

import com.softserve.academy.models.Product;
import com.softserve.academy.repositories.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductDAO productDAO;

    @Autowired
    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product) {
        productDAO.save(product);
    }

    @GetMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product readProductById(@PathVariable long id) {
        return productDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not Found"));
    }

    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable long id, @RequestBody Product product) {
        Product newProduct = productDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not Found"));

        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct.setQuantity(product.getQuantity());

        productDAO.save(newProduct);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable long id) {
        Product product = productDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not Found"));

        productDAO.delete(product);
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> readAllProducts() {
        return productDAO.findAll();
    }
}
