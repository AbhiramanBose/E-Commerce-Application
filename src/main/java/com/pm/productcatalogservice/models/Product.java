package com.pm.productcatalogservice.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
//@JsonPropertyOrder({
//        "id",
//        "name",
//        "category",
//        "description",
//        "price",
//        "state",
//        "imageUrl",
//        "createdAt",
//        "lastUpdatedAt"
//})
public class Product extends BaseModel {
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Category category;
    private Boolean isPrime;
}
