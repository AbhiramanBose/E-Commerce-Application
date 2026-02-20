package com.pm.productcatalogservice.controllers;

import com.pm.productcatalogservice.dtos.CategoryDto;
import com.pm.productcatalogservice.dtos.ProductDto;
import com.pm.productcatalogservice.models.Category;
import com.pm.productcatalogservice.models.Product;
import com.pm.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts(){
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products= productService.getAllProducts();
        for(Product product:products){
            productDtos.add(from(product));
        }
        return productDtos;
//        Product product = new Product();
//        product.setId(1L);
//        product.setName("Iphone");
//        List<Product> products = new ArrayList<>();
//        products.add(product);
//        return products;
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> findProductById(@PathVariable Long productId){
//        long productId = id;
//        Product product = new Product();
//        product.setId(id);
//        return product;
        try{
        MultiValueMap<String, String> headers=new LinkedMultiValueMap<>();
        if(productId<=0){
            headers.add("called","bhudwak");
            //return new ResponseEntity<>(null,headers, HttpStatus.BAD_REQUEST);
            throw new IllegalArgumentException("Please try with product ID greater than 0");

        }
        Product product=productService.getProductById(productId); //Dependency Inversion
        headers.add("called by","intelligent");
        if(product==null){
            return new ResponseEntity<>(null,headers,HttpStatus.BAD_REQUEST);
        }
//        return from(product);
        return new ResponseEntity<>(from(product),headers,HttpStatus.OK);
        }
        catch(IllegalArgumentException exception){
            throw exception;
        }
    }


    private ProductDto from(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory()!=null){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }
    private Product from(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        if(productDto.getCategory()!=null){
            Category category = new Category();
            category.setName(productDto.getCategory().getName());


            product.setCategory(category);
        }
        return product;
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto product){
        Product f= productService.createProduct(from(product));
        return from(f);
    }

    @PutMapping("/{id}")
    public ProductDto replaceProduct(@PathVariable Long id, @RequestBody ProductDto request){
        Product product = productService.replaceProduct(id,from(request));
        return from(product);
    }
}
