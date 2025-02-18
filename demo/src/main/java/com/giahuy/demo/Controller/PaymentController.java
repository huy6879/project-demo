package com.giahuy.demo.Controller;


import com.giahuy.demo.Service.PaymentService;
import com.giahuy.demo.Service.ReceiptService;
import com.giahuy.demo.dto.response.ApiResponse;
import com.giahuy.demo.dto.response.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    PaymentService paymentService;
    ReceiptService receiptService;
//    @GetMapping("/vn-pay")
//    public ApiResponse<PaymentResponse> pay(HttpServletRequest request) {
//        return ApiResponse.<PaymentResponse>builder()
//                .result(paymentService.createVnPayPayment())
//                .build();
//    }

    @GetMapping("/vn-pay-callback")
    public ApiResponse<PaymentResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");

        if (status != null && status.equals("00")) {
            // Trả về ApiResponse với mã trạng thái 1000 (thành công)
            PaymentResponse response = new PaymentResponse("00", "Success", "");
            return ApiResponse.<PaymentResponse>builder()
                    .message("Success")
                    .result(response)                   // Dữ liệu trả về
                    .build();
        } else {
            return ApiResponse.<PaymentResponse>builder()
                    .code(400)                        // Mã trạng thái thất bại
                    .message("Failed")                  // Thông điệp lỗi
                    .result(null)                       // Không có dữ liệu trả về
                    .build();
        }
    }

//    @GetMapping("/vn-pay-callback")
//    public boolean addReceipt(@RequestBody Map<String, Cart> carts, HttpServletRequest request) {
//
//        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
//
//        String status = request.getParameter("vnp_ResponseCode");
//
//        if (status != null && status.equals("00")) {
//            return receiptService.addReceipt(carts,vnp_TxnRef);
//        }
//        return false;
//    }


}
