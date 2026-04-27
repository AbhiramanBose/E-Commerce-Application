package com.pm.productcatalogservice.controllers;

import com.pm.productcatalogservice.dtos.ProductDto;
import com.pm.productcatalogservice.models.Product;
import com.pm.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;


    @MockBean//(name = "sps")
    private IProductService productService;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Test
    @DisplayName("get product with id 4 will run fine")
    public void Test_GetProductById_ReturnsProductSuccessfully(){
        //Arrange
        Long productId=4L;
        Product product=new Product();
        product.setId(productId);
        product.setName("Iphone");
        when(productService.getProductById(productId)).thenReturn(product);

        //Act
        ResponseEntity<ProductDto> response =productController.findProductById(productId);

        //Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        //assertEquals(productId,response.getBody().getId());
        assertEquals("Iphone",response.getBody().getName());
        verify(productService,times(1)).getProductById(4L);
    }

    @Test
    public void Test_GetProductById_CalledWithInvalidId_ResultsInIllegalArgumentException(){
        //Act and Assert
        Exception exception=assertThrows(IllegalArgumentException.class,
                ()->productController.findProductById(-1L));

        assertEquals("Please try with product ID greater than 0",exception.getMessage());
        verify(productService,never()).getProductById(anyLong());
    }

    @Test
    public void Test_GetProductById_ProductServiceCalledWithCorrectArguments_RunSuccessfully(){
        //Arrange
        Long productId=4L;
        Product product=new Product();
        product.setId(productId);
        product.setName("Iphone");
        when(productService.getProductById(productId)).thenReturn(product);

        //Act
        productController.findProductById(productId);

        //Assert
        verify(productService).getProductById(idCaptor.capture());
        assertEquals(4L,idCaptor.getValue());
    }
}