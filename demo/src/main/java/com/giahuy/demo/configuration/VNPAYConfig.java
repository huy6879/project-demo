package com.giahuy.demo.configuration;

import com.giahuy.demo.utils.VNPAYUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
public class VNPAYConfig {
    @Getter
    @Value("${payment.vnPAY.url}")
    private String vnp_PayUrl;
    @Value("${payment.vnPAY.returnUrl}")
    private String vnp_ReturnUrl;
    @Value("${payment.vnPAY.tmnCode}")
    private String vnp_TmnCode ;
    @Getter
    @Value("${payment.vnPAY.secretKey}")
    private String secretKey;
    @Value("${payment.vnPAY.version}")
    private String vnp_Version;
    @Value("${payment.vnPAY.command}")
    private String vnp_Command;
    @Value("${payment.vnPAY.orderType}")
    private String orderType;

    public Map<String, String> getVNPayConfig() {
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Version", this.vnp_Version);
        vnpParamsMap.put("vnp_Command", this.vnp_Command);
        vnpParamsMap.put("vnp_TmnCode", this.vnp_TmnCode);
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef", VNPAYUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang:" +  VNPAYUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderType", this.orderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", this.vnp_ReturnUrl);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
        return vnpParamsMap;
    }
}