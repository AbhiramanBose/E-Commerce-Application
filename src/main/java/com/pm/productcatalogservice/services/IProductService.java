package com.pm.productcatalogservice.services;

import com.pm.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long productId);
    List<Product> getAllProducts();
    Product replaceProduct(Long productId, Product request);
    Product createProduct(Product request);
}
