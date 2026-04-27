package com.pm.productcatalogservice.services;

import com.pm.productcatalogservice.models.Product;
import com.pm.productcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service("sps")
@Primary
public class StorageProductService implements IProductService{

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product getProductById(Long productId) {
        Optional<Product> productOptional=productRepo.findProductById(productId);
        if(productOptional.isEmpty()){
            return null;
        }
        return productOptional.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product replaceProduct(Long productId, Product request) {
        return null;
    }

    @Override
    public Product createProduct(Product request) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return productRepo.save(product);
    }


}
