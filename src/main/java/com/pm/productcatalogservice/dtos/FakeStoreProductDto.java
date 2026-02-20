package com.pm.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String image;
    private String category;
    public FakeStoreProductDto() {
        List<FakeStoreProductDto> products = new ArrayList<>();
    }
}
