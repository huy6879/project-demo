package com.giahuy.demo.dto.request;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {

    Integer id;

    String name;

    Integer price;

    Integer quantity;

    Integer unit;

    @Nullable
    String category_id;

}
