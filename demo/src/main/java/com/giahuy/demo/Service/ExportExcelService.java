package com.giahuy.demo.Service;

import com.giahuy.demo.entity.Statics;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportExcelService {

    public byte[] exportToExcel(List<Statics> dataList) throws IOException {
        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();  // Tạo đối tượng Workbook

        // Tạo sheet trong workbook
        Sheet sheet = workbook.createSheet("Report");

        // Tạo row đầu tiên cho tiêu đề cột
        Row headerRow = sheet.createRow(0);

        // Thiết lập tên cột
        String[] headers = {"Date", "Revenue"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Thêm dữ liệu vào sheet
        int rowNum = 1; // Bắt đầu từ dòng thứ 2
        for (Statics data : dataList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.getDate().toString());  // Dữ liệu ngày tháng
            row.createCell(1).setCellValue(data.getRevenue());  // Dữ liệu doanh thu
        }

        // Tự động điều chỉnh kích thước cột để vừa với nội dung
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi dữ liệu vào output stream
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);  // Ghi dữ liệu vào ByteArrayOutputStream
            return byteArrayOutputStream.toByteArray();  // Trả về file Excel dưới dạng byte array
        } finally {
            workbook.close();  // Đảm bảo đóng workbook sau khi sử dụng
        }
    }
}
