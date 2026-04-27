package com.pm.productcatalogservice.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.pm.productcatalogservice.models.State;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private CategoryDto category;

}
