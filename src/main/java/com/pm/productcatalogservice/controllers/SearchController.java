package com.pm.productcatalogservice.controllers;

import com.pm.productcatalogservice.dtos.SearchRequestDto;
import com.pm.productcatalogservice.models.Product;
import com.pm.productcatalogservice.services.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ISearchService searchService;

    @PostMapping
    public Page<Product> searchProducts(@RequestBody SearchRequestDto searchRequestDto){
        return searchService.searchProducts(searchRequestDto.getSearchQuery(),searchRequestDto.getPageSize(),searchRequestDto.getPageNumber(),searchRequestDto.getSortParamList());
    }
}
