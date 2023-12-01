package com.store.controllers;

import com.store.DTO.ProductDTO;
import com.store.entities.Product;
import com.store.exceptions.MiException;
import com.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/")
    public List<Product> getProducts(){
        return productService.getProducts();
    }
    @PostMapping("/create")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) throws MiException {
        return productService.createProduct(productDTO);
    }
    @PutMapping("/modify/{id}")
    public ProductDTO modifyProductById(@PathVariable Long id, @RequestBody ProductDTO productDTO) throws MiException{
        return productService.modifyProduct(id, productDTO);
    }
    @DeleteMapping("/delete/{id}")
    public void deactivateProductById(@PathVariable Long id) throws MiException{
        productService.deactivateProduct(id);
    }
    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) throws MiException {
        return productService.getProductById(id);
    }
}
