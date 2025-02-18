package com.giahuy.demo.mapper;

import com.giahuy.demo.dto.request.ProductCreationRequest;
import com.giahuy.demo.dto.response.CategoryResponse;
import com.giahuy.demo.dto.response.ProductResponse;
import com.giahuy.demo.entity.Category;
import com.giahuy.demo.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-17T16:07:12+0700",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.2 (Amazon.com Inc.)"
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
        product.unit( request.getUnit() );

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
        productResponse.category_id( categoryToCategoryResponse( product.getCategory_id() ) );

        return productResponse.build();
    }

    protected CategoryResponse categoryToCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse.CategoryResponseBuilder categoryResponse = CategoryResponse.builder();

        categoryResponse.id( category.getId() );
        categoryResponse.name( category.getName() );
        categoryResponse.description( category.getDescription() );

        return categoryResponse.build();
    }
}
