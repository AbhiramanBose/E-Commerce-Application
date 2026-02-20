package com.pm.productcatalogservice.clients;

import com.pm.productcatalogservice.dtos.FakeStoreProductDto;
import com.pm.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeStoreApiClient {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod , @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    public FakeStoreProductDto getProductById(Long productId){

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity=requestForEntity("http://fakestoreapi.com/products/{productId}",HttpMethod.GET,null,FakeStoreProductDto.class,productId);
        return validateFakeStoreResponse(fakeStoreProductDtoResponseEntity);

//        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity=
//                restTemplate
//                        .getForEntity("http://fakestoreapi.com/products/{productId}",
//                                FakeStoreProductDto.class, productId);
//        return from(fakeStoreProductDto);
//        return null;
    }

    private FakeStoreProductDto validateFakeStoreResponse(ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity){
        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200))&& fakeStoreProductDtoResponseEntity.getBody()!=null){
            return fakeStoreProductDtoResponseEntity.getBody();
        }
        return null;
    }
}
