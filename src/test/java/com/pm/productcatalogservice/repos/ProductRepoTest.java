package com.pm.productcatalogservice.repos;

import com.pm.productcatalogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepoTest {
    @Autowired
    private ProductRepo productRepo;

    @Test
    public void insertDataInRDS(){
        Product product1 =new Product();
        product1.setId(1L);
        product1.setName("Pen");
        product1.setPrice(100.0);

        Product product2= new Product();
        product2.setName("MacBook M1");
        product2.setPrice(100000.0);
        product2.setId(2L);
        productRepo.save(product1);
        productRepo.save(product2);
    }

    //@Test
    public void testJpa(){
//        List<Product> productList=productRepo.findProductByOrderByPrice();
//        for(Product product:productList){
//            System.out.println(product.getPrice());
//        }
    }

    @Test
    public void testFindProductTitleByID(){
        String title=productRepo.findProductTitleByID(1L);
        System.out.println(title);
    }

    @Test
    public void testFindCategoryNameFromProductId(){
        String categoryName=productRepo.findCategoryNameFromProductId(1L);
        System.out.println(categoryName);
    }




}