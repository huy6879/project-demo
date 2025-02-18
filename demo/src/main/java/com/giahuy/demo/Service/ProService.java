package com.giahuy.demo.Service;


import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.giahuy.demo.dto.request.ProductCreationRequest;
import com.giahuy.demo.dto.response.ProductResponse;
import com.giahuy.demo.entity.Category;
import com.giahuy.demo.entity.Product;
import com.giahuy.demo.exception.AppException;
import com.giahuy.demo.exception.ErrorCode;
import com.giahuy.demo.mapper.ProdMapper;
import com.giahuy.demo.repository.CateRepository;
import com.giahuy.demo.repository.ProductRepository;
import com.giahuy.demo.repository.SearchProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProService{

    ProductRepository productRepository;
    ProdMapper prodMapper;
    CateRepository cateRepository;

    SearchProductRepository searchProductRepository;

    RedisTemplate<String, Object> redisTemplate;

    private static final String PRODUCT_CACHE_KEY = "all_products";


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

    public List<ProductResponse> getAllProducts() {
        // Kiểm tra cache Redis xem đã có dữ liệu chưa
        List<ProductResponse> cachedProducts = (List<ProductResponse>) redisTemplate.opsForValue().get(PRODUCT_CACHE_KEY);

        if (cachedProducts != null) {
            // Nếu có, trả về danh sách sản phẩm từ cache
            log.info("Cache hit - products found in Redis");
            return cachedProducts;
        }

        // Nếu không có, truy vấn database
        log.info("Cache miss - fetching products from DB");
        List<ProductResponse> products = productRepository.findAll().stream()
                .map(prodMapper::toProductResponse)
                .collect(Collectors.toList());

        // Lưu danh sách sản phẩm vào cache Redis mà không có @class
        redisTemplate.opsForValue().set(PRODUCT_CACHE_KEY, products, 10, TimeUnit.MINUTES); // Lưu cache trong 10 phút

        return products;
    }

    public List<ProductResponse> getAllUsersAndSearchWithKwAndSorting(String search, String sort) {
        return searchProductRepository.getALlProductsWithSortByColumnSearch(search,sort);
    }


}
