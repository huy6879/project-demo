package com.giahuy.demo.mapper;

import com.giahuy.demo.dto.response.StaticsResultResponse;
import com.giahuy.demo.entity.Statics;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-17T16:07:12+0700",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.2 (Amazon.com Inc.)"
)
@Component
public class StaticsMapperImpl implements StaticsMapper {

    @Override
    public StaticsResultResponse toStaticsResponse(Statics statics) {
        if ( statics == null ) {
            return null;
        }

        StaticsResultResponse staticsResultResponse = new StaticsResultResponse();

        staticsResultResponse.setDate( statics.getDate() );
        staticsResultResponse.setRevenue( statics.getRevenue() );

        return staticsResultResponse;
    }
}
