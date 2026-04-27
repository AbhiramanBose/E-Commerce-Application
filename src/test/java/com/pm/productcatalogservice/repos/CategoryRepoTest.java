package com.pm.productcatalogservice.repos;

import com.pm.productcatalogservice.models.Category;
import com.pm.productcatalogservice.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepoTest {

    @Autowired
    private CategoryRepo categoryRepo;

    //@Test
    //@Transactional
    void testFetchTypes(){
        Category category= categoryRepo.findById(212L).get();
        for(Product product : category.getProducts()){
            System.out.println(product.getName());
        }

    }
    //@Test
    //@Transactional
    void testSomething(){
        List<Category> categoryList=categoryRepo.findAll();
        for(Category category:categoryList){
            for(Product product:category.getProducts()){
                System.out.println(product.getName());
            }
        }
        //Problems in SUBSELECT: Space increases
    }

}