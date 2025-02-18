package com.giahuy.demo.mapper;


import com.giahuy.demo.dto.response.StaticsResultResponse;
import com.giahuy.demo.dto.response.UserResponse;
import com.giahuy.demo.entity.Statics;
import com.giahuy.demo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StaticsMapper {

    StaticsResultResponse toStaticsResponse(Statics statics);

}
