package com.giahuy.demo.Controller;


import com.giahuy.demo.Service.ProService;
import com.giahuy.demo.dto.response.ApiResponse;
import com.giahuy.demo.dto.request.ProductCreationRequest;
import com.giahuy.demo.dto.response.ProductResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prods")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProdController {

    ProService prodService;

    @PostMapping
    ApiResponse<ProductResponse> create(@RequestBody ProductCreationRequest request){
        return ApiResponse.<ProductResponse>builder()
                .result(prodService.addProduct(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ProductResponse>> getAll(){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(prodService.getAllProducts())
                .build();
    }


    @GetMapping("/list-with-sort-by-multiple-columns")
    ApiResponse<List<ProductResponse>> getAllUsersWithSortByMultipleColumns(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(prodService.getAllUsersAndSearchWithKwAndSorting(search,sort))
                .build();
    }


}
