package com.ecommerce.service.impl;

import com.ecommerce.exceptions.ProductAlreadyPresentException;
import com.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.models.ProductModel;
import com.ecommerce.repo.ProductRepository;
import com.ecommerce.service.CatalogService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// repository: to manage data in data-source
// service : talks to repository and contains all business logic
public class CatalogServiceImpl implements CatalogService {

    private static final Logger log = Logger.getLogger(CatalogServiceImpl.class.getName());
    private ProductRepository productRepository;

    public CatalogServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(ProductModel p) {
        try {
            productRepository.addProduct(p);
        } catch (IOException e) {
            e.printStackTrace();
            log.severe("IOException occurred, product not added " + e.getMessage());
        } catch (ProductAlreadyPresentException e) {
            e.printStackTrace();
            log.severe("ProductAlreadyPresentException occurred "  + e.getMessage());
        }
        log.info("Product added successfully");
    }

    @Override
    public ProductModel findBySku(String sku) {
        ProductModel p = null;
        try {
            p = productRepository.getProductBySku(sku);
            log.info("Product found ");
        } catch (IOException e) {
            log.severe("IOException occurred, product not found " + e.getMessage());
        } catch (ProductNotFoundException e) {
            log.severe("ProductNotFoundException occurred, product not found " + e.getMessage());
        }
        return p;
    }

    @Override
    public ProductModel findById(String id) {
        ProductModel p = null;
        try {
            p = productRepository.getProductById(id);
            if(p != null) {
                log.info("Product found ");
            }else{
                log.info("Product not found");
                throw new ProductNotFoundException("Product not found with id " + id);
            }
        } catch (IOException e) {
            log.severe("IOException occurred, product not found " + e.getMessage());
        } catch (ProductNotFoundException e) {
            log.severe("ProductNotFoundException occurred, product not found " + e.getMessage());
        }
        return p;
    }

    @Override
    public void updateProduct(ProductModel p) {
        try {
           productRepository.updateProduct(p);
        } catch (IOException e) {
            log.severe("IOException occurred, product not found " + e.getMessage());
        } catch (ProductNotFoundException e) {
            log.severe("ProductNotFoundException occurred, product not found " + e.getMessage());
        }
    }

    @Override
    public void removeProductById(String id) {
        try {
            productRepository.removeProductById(id);
            log.info("Product removed successfully");
        } catch (IOException e) {
            log.severe("IOException occurred, product not found " + e.getMessage());
        } catch (ProductNotFoundException e) {
            log.severe("ProductNotFoundException occurred, product not found " + e.getMessage());
        }
    }

    @Override
    public void removeProductBySku(String sku) {
        try {
            productRepository.removeProductBySku(sku);
            log.info("Product removed successfully");
        } catch (IOException e) {
            log.severe("IOException occurred, product not found " + e.getMessage());
        } catch (ProductNotFoundException e) {
            log.severe("ProductNotFoundException occurred, product not found " + e.getMessage());
        }
    }

    @Override
    public boolean isStockPresent(String sku, int quantity) {
        try {
            ProductModel p = productRepository.getProductBySku(sku);
            if(p == null){
                throw new ProductNotFoundException("Product not found with id " + sku);
            }
            if(p.getAvailableQuantity() >= quantity){
                log.info("Enough stock present for the product with sku: " + sku);
                return true;
            }
        } catch (IOException e) {
            log.severe("IOException occurred, product not found " + e.getMessage());
        } catch (ProductNotFoundException e) {
            log.severe("ProductNotFoundException occurred, product not found " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<ProductModel> search(String q) {
        List<ProductModel> products = null;
        boolean isFound = false;
        try {
            products = productRepository.findAll();
        } catch (IOException e) {
            log.severe("IOException occurred, product not found " + e.getMessage());
        }
        List<ProductModel> res = new ArrayList<>();
        for (ProductModel p :products){
            if(p.getName().equalsIgnoreCase(q) || p.getName().contains(q)){
                res.add(p);
                isFound = true;
                log.info("Found matching product by name");
            }
            if(p.getSku().equalsIgnoreCase(q)){
                res.add(p);
                isFound = true;
                log.info("Found matching product by id");
            }
        }
        if(!isFound){
            log.severe("Related product not found");
        }
        return res;
    }

    @Override
    public List<ProductModel> findAll() {
        try {
            return productRepository.findAll();
        } catch (IOException e) {
            log.severe("IOException occurred, product not found " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
