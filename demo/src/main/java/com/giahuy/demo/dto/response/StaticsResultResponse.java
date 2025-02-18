package com.giahuy.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaticsResultResponse {

    @JsonFormat(pattern = "yyyy-MM")
    @NotNull(message = "Date cannot be null")
    Date date;
    int revenue;

    public StaticsResultResponse(Date date, int revenue) {
        this.date = date; // Chuyển LocalDateTime thành LocalDate
        this.revenue = revenue; // Sử dụng Double thay vì Long
    }



}
