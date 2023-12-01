package com.store.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.DTO.ProductDTO;
import com.store.entities.Product;
import com.store.exceptions.MiException;
import com.store.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ObjectMapper mapper;

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) throws MiException{
        validate(productDTO.getName(), productDTO.getPrice());
        Product product;
        product = mapper.convertValue(productDTO, Product.class);
        product.setUploadedDate(LocalDate.now());
        productRepository.save(product);
        return productDTO;
    }
    @Transactional
    public ProductDTO modifyProduct(Long id, ProductDTO productDTO) throws MiException{
        validate(productDTO.getName(), productDTO.getPrice());
        Optional<Product> response = productRepository.findById(id);
        if (response.isPresent()){
            Product product = response.get();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            productRepository.save(product);
        }
        return productDTO;
    }
    @Transactional
    public void deactivateProduct(Long id) throws MiException{
        if(id <= 0){
            throw new MiException("Id cannot be empty or null");
        }
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setActive(false);
        productRepository.save(product);
    }


    public List<Product> getProducts(){
        return (ArrayList<Product>) productRepository.findAll();
    }
    public Product getProductById(Long id) throws MiException{
        if(id <= 0){
            throw new MiException("Id cannot be empty or null");
        }
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        return product;
    }
    public void validate(String name, double price) throws MiException {
        if(name == null || name.trim().isEmpty()){
            throw new MiException("Name cannot be empty or null");
        }
        if(price <= 0){
            throw new MiException("Price cannot be zero or lower");
        }

    }
}
