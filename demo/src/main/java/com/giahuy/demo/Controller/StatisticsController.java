package com.giahuy.demo.Controller;


import com.giahuy.demo.Service.ExportExcelService;
import com.giahuy.demo.Service.StaticsService;
import com.giahuy.demo.dto.response.ApiResponse;
import com.giahuy.demo.dto.response.StaticsResultResponse;
import com.giahuy.demo.dto.response.UserResponse;
import com.giahuy.demo.entity.Statics;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticsController {

    StaticsService staticsService;

    ExportExcelService exportExcelService;


    @GetMapping("/total-sales")
    public ApiResponse<StaticsResultResponse> getTotalSalesByDate(@RequestParam("date") String date) {
        // Trả về ApiResponse với kết quả
        return ApiResponse.<StaticsResultResponse>builder()
                .result(staticsService.getStaticsByDate(date))
                .build();
    }

    @GetMapping("/total-sales-months")
    public ApiResponse<List<StaticsResultResponse>> getTotalSalesByMonths() {
        // Trả về ApiResponse với kết quả
        return ApiResponse.<List<StaticsResultResponse>>builder()
                .result(staticsService.getStaticsByMonths())
                .build();
    }


    @GetMapping("/export-sales")
    public ResponseEntity<byte[]> exportSalesToExcel(@RequestParam("date") String date) {
        log.info("Exporting sales data to Excel for date: {}", date);

        // Lấy danh sách thống kê doanh thu theo ngày từ service
        StaticsResultResponse staticsList = staticsService.getStaticsByDate(date);

        try {
            // Gọi service ExportExcelService để xuất ra file Excel
            byte[] excelFile = exportExcelService.exportToExcel((List<Statics>) staticsList);

            // Cấu hình headers để trả về file Excel
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=sales_report.xlsx");
            headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            // Trả về ResponseEntity chứa file Excel dưới dạng byte array
            return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Error exporting data to Excel", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
