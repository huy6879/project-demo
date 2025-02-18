package com.giahuy.demo.repository;

import com.giahuy.demo.dto.response.ApiResponse;
import com.giahuy.demo.dto.response.ProductResponse;
import com.giahuy.demo.entity.Product;
import com.giahuy.demo.mapper.ProdMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.giahuy.demo.utils.AppConst.SORT_BY;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Repository
public class SearchProductRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ProdMapper prodMapper;


    public List<ProductResponse> getALlProductsWithSortByColumnSearch(String search, String sortBy) {

        StringBuilder sql = new StringBuilder("select p.* from product p ");

        sql.append("left join category c on p.category_id = c.id where 1=1");


        if(StringUtils.hasLength(search)) {
            sql.append(" and (p.name like '%").append(search).append("%' ");
            sql.append(" or c.name like '%").append(search).append("%')");
        }

        if (StringUtils.hasLength(sortBy)) {
            // firstName:asc|desc
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);

            if (matcher.find()) {
                sql.append(String.format("Order By p.%s %s", matcher.group(1), matcher.group(3)));
            }

        }


        Query query = entityManager.createNativeQuery(sql.toString(), Product.class);

        List<Product> products = query.getResultList();
        System.out.println(products);


        List<ProductResponse> productResponses = products.stream()
                .map(prodMapper :: toProductResponse)
                .collect(Collectors.toList());


        return productResponses;


    }


    

}
