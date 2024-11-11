package com.giahuy.demo.mapper;

import com.giahuy.demo.dto.request.ProductCreationRequest;
import com.giahuy.demo.dto.response.ProductResponse;
import com.giahuy.demo.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-11T15:13:01+0700",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ProdMapperImpl implements ProdMapper {

    @Override
    public Product toProduct(ProductCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( request.getId() );
        product.name( request.getName() );
        product.price( request.getPrice() );
        product.quantity( request.getQuantity() );

        return product.build();
    }

    @Override
    public ProductResponse toProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.id( product.getId() );
        productResponse.name( product.getName() );
        productResponse.price( product.getPrice() );
        productResponse.quantity( product.getQuantity() );
        productResponse.unit( product.getUnit() );
        productResponse.category( product.getCategory().getName() );

        return productResponse.build();
    }
}
