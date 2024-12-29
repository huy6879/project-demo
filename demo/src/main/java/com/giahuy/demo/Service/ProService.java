package com.giahuy.demo.Service;


import com.giahuy.demo.dto.request.ProductCreationRequest;
import com.giahuy.demo.dto.response.ProductResponse;
import com.giahuy.demo.entity.Category;
import com.giahuy.demo.entity.Product;
import com.giahuy.demo.exception.AppException;
import com.giahuy.demo.exception.ErrorCode;
import com.giahuy.demo.mapper.ProdMapper;
import com.giahuy.demo.repository.CateRepository;
import com.giahuy.demo.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProService {

    ProductRepository productRepository;
    ProdMapper prodMapper;
    CateRepository cateRepository;

    public ProductResponse addProduct(ProductCreationRequest request) {

        if(productRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.PROD_EXISTED);

        Product prod = prodMapper.toProduct(request);

        Category category = cateRepository.findById(request.getCategory_id())
                .orElseThrow(() -> new AppException(ErrorCode.CATE_NOT_EXISTED));

        prod.setCategory_id(category);

        prod = productRepository.save(prod);

        return prodMapper.toProductResponse(prod);
    }

    @PreAuthorize("permitAll()")
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(prodMapper::toProductResponse).toList();
    }



}
