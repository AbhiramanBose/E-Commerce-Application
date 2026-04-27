package com.pm.productcatalogservice.services;

import com.pm.productcatalogservice.dtos.SortParam;
import com.pm.productcatalogservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISearchService {

    public Page<Product> searchProducts(String searchQuery, int pageSize, int pageNumber, List<SortParam> sortParams);
}
