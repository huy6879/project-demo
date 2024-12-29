package com.giahuy.demo.dto;

import com.giahuy.demo.Service.PaymentService;
import com.giahuy.demo.Service.ReceiptService;
import com.giahuy.demo.dto.response.PaymentResponse;
import com.giahuy.demo.entity.Cart;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/receipt")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiptController {

    ReceiptService receiptService;
    PaymentService paymentService;

    // Phương thức addReceipt sẽ trả về PaymentResponse nếu thanh toán thành công
    @PostMapping("/payment")
    public PaymentResponse addPayment(@RequestBody Map<String, Cart> carts, HttpServletRequest request) {
        try {
            // Lấy token từ header Authorization
            String token = request.getHeader("Authorization");

            log.info(token);
            // Lưu token vào session
            request.getSession().setAttribute("token", token);

            Integer totalAmount = receiptService.calculateTotalAmount(carts);

            PaymentResponse paymentResponse = paymentService.createVnPayPayment(carts, totalAmount, request);

            if (paymentResponse != null) {
                return paymentResponse;
            } else {
                log.error("Thanh toán không thành công, mã lỗi: " + paymentResponse.getCode());
                // Trả về thông báo lỗi nếu thanh toán thất bại
                return PaymentResponse.builder()
                        .code("99")  // Mã trạng thái thất bại
                        .message("Thanh toán không thành công")
                        .build();
            }
        } catch (Exception e) {
            log.error("Có lỗi xảy ra khi tạo đơn hàng và thanh toán", e);
            // Trả về thông báo lỗi nếu có lỗi trong quá trình xử lý
            return PaymentResponse.builder()
                    .code("99")  // Mã trạng thái thất bại
                    .message("Có lỗi xảy ra trong quá trình xử lý")
                    .build();
        }
    }

    @PostMapping("/add")
    public boolean addReceipt(@RequestBody Map<String, Cart> carts, HttpServletRequest request) {

        return receiptService.addReceipt(carts);
    }


}
