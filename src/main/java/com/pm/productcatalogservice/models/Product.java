package com.pm.productcatalogservice.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


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
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product extends BaseModel {
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;
    private Boolean isPrime;

    public Product() {
        setCreatedAt(new Date());
        setLastUpdatedAt(new Date());
        setState(State.ACTIVE);
    }
}
