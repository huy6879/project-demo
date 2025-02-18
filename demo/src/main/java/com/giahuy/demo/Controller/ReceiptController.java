package com.giahuy.demo.Controller;


import com.giahuy.demo.Service.PaymentService;
import com.giahuy.demo.Service.ReceiptService;
import com.giahuy.demo.dto.response.PaymentResponse;
import com.giahuy.demo.entity.Cart;
import com.giahuy.demo.utils.VNPAYUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/receipt")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiptController {

    ReceiptService receiptService;
    PaymentService paymentService;

    @PostMapping("/payment")
    public PaymentResponse addReceipt(@RequestBody Map<String, Cart>carts, HttpServletRequest request) {
        try {
            
            Integer totalAmount = receiptService.calculateTotalAmount(carts);

            String vnp_TxnRef = VNPAYUtil.getRandomNumber(8);

            receiptService.addReceipt(carts, vnp_TxnRef);

            PaymentResponse paymentResponse = paymentService.createVnPayPayment(carts, vnp_TxnRef, totalAmount, request);

            if(paymentResponse != null) {
                return paymentResponse;
            } else {
                log.error("Failed to create payment response, code : " + paymentResponse.getCode());
                return paymentResponse.builder()
                        .code("99")
                        .message("Failed to create payment response")
                        .build();
            }
        } catch (Exception e) {
            log.error("Failed to add receipt", e);
            return PaymentResponse.builder()
                    .code("99")
                    .message("Failed to add receipt")
                    .build();
        }
    }

}
