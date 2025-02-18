package com.giahuy.demo.Service;

import com.giahuy.demo.dto.response.StaticsResultResponse;
import com.giahuy.demo.entity.OrderDetails;
import com.giahuy.demo.entity.Statics;
import com.giahuy.demo.mapper.StaticsMapper;
import com.giahuy.demo.repository.OrderDetailRepository;
import jakarta.persistence.Tuple;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaticsService {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    StaticsMapper staticsMapper;


    @PreAuthorize("hasRole('ADMIN')")
    public StaticsResultResponse getStaticsByDate(String date) {
        try {
            Date sqlDate = Date.valueOf(date);

            List<Tuple> result = orderDetailRepository.findRevenueByDate(sqlDate);

            // If result is not empty, create Statics object
            if (!result.isEmpty()) {
                Tuple tuple = result.get(0);

                Date resultDate = tuple.get("date", Date.class);
                Long revenue = tuple.get("revenue", Long.class);

                Statics statics = new Statics(resultDate, revenue.intValue());

                return staticsMapper.toStaticsResponse(statics);

            } else {
                throw new IllegalArgumentException("No data found for the given date");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format, please use 'yyyy-MM-dd'", e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<StaticsResultResponse> getStaticsByMonths() {
        try {
            // Retrieve the revenue data grouped by months
            List<Tuple> result = orderDetailRepository.findRevenueByMonths();

            // Initialize a map to store the revenue for each month (1-12)
            Map<Integer, Long> monthlyRevenue = new HashMap<>();
            for (int i = 1; i <= 12; i++) {
                monthlyRevenue.put(i, 0L);  // Default revenue is 0 for each month
            }

            // Populate the map with the actual revenue data
            for (Tuple tuple : result) {
                int month = tuple.get("month", Integer.class);
                Long revenue = tuple.get("revenue", Long.class);
                monthlyRevenue.put(month, revenue);  // Set revenue for the given month
            }

            // Convert the monthlyRevenue map into a list of Statics objects
            List<Statics> responses = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                // Create a new Statics object for each month
                Statics statics = new Statics();

                // Set the date to the first day of each month
                statics.setDate(Date.valueOf(LocalDate.of(2025, i, 1))); // You can change the year if needed

                // Set the revenue for the month
                statics.setRevenue(monthlyRevenue.get(i).intValue()); // Convert Long to int

                // Add the Statics object to the list
                responses.add(statics);
            }

            // Map Statics objects to StaticsResultResponse and return the list
            List<StaticsResultResponse> responsesResult = responses.stream()
                    .map(staticsMapper::toStaticsResponse)
                    .collect(Collectors.toList());

            return responsesResult;

        } catch (Exception e) {
            throw new IllegalArgumentException("No data found for the given months", e);
        }
    }

}







