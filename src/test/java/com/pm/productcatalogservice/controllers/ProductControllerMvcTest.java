package com.pm.productcatalogservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.productcatalogservice.dtos.ProductDto;
import com.pm.productcatalogservice.models.Product;
import com.pm.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerMvcTest {

    @MockBean//(name = "sps")
    private IProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void Test_GetAllProducts_RunsSuccessfully() throws Exception {
        Product product1=new Product();
        product1.setId(1L);
        product1.setName("Iphone");
        Product product2=new Product();
        product2.setId(2L);
        product2.setName("Samsung");
        product2.setPrice(100000.00);
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        when(productService.getAllProducts()).thenReturn(productList);

        ProductDto productDto1 = new ProductDto();
        productDto1.setName("Iphone");
        productDto1.setId(1L);
        ProductDto productDto2 = new ProductDto();
        productDto2.setName("Samsung");
        productDto2.setId(2L);
        productDto2.setPrice(100000.00);
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(productDto1);
        productDtoList.add(productDto2);
         mockMvc.perform(get("/products"))
                 .andExpect(status().isOk())
                 .andExpect(content().string(objectMapper.writeValueAsString(productDtoList)))
                 .andExpect(jsonPath("$.length()").value(2))
                 .andExpect(jsonPath("$[0].name").value("Iphone"))
                 .andExpect(jsonPath("$[1].price").value(100000.00))
                 .andExpect(jsonPath("$[1].length()").value(3))
                 //.andExpect(jsonPath("$[1].name.length()").value(0))
         ;
    }
    @Test
    public void Test_CreateProduct_RunsSuccessfulty() throws Exception{
        //Arrange
        Product product=new Product();
        product.setId(5L);
        product.setName("Ipad");
        when(productService.save(any(Product.class))).thenReturn(product);

        ProductDto productDto=new ProductDto();
        productDto.setName("Ipad");
        productDto.setId(5L);

        //Act and Assert
        mockMvc.perform(post("/products")
                        .content(objectMapper.writeValueAsString(productDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)))
                .andExpect(jsonPath("$.id").value(productDto.getId()))
                .andExpect(jsonPath("$.name").value(productDto.getName()))

        ;

    }
}
