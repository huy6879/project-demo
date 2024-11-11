package com.giahuy.demo.mapper;


import com.giahuy.demo.dto.request.ProductCreationRequest;
import com.giahuy.demo.dto.response.ProductResponse;
import com.giahuy.demo.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProdMapper {

    Product toProduct(ProductCreationRequest request);

    @Mapping(target = "category", ignore = true)
    ProductResponse toProductResponse(Product product);

}
