package com.giahuy.demo.Service;

import com.giahuy.demo.dto.request.UserChangePasswordRequest;
import com.giahuy.demo.entity.ForgotPassword;
import com.giahuy.demo.entity.User;
import com.giahuy.demo.exception.AppException;
import com.giahuy.demo.exception.ErrorCode;
import com.giahuy.demo.repository.ForgotPasswordRepository;
import com.giahuy.demo.repository.UserRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class EmailService {

    @Autowired
    private SendGrid sendGrid;

    @Autowired
    UserRepository userRepository;

    @Value("${spring.sendGrid.fromEmail}")
    private String from;

    @Value("${spring.sendGrid.templateId}")
    private String templateId;

    @Value("${spring.sendGrid.verificationLink}")
    private String verificationLink;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;



//    public void sendVerificationEmail(String to, String username, String password) throws IOException {
//        log.info("Sending verification email for name={}", username);
//
//        Email fromEmail = new Email(from, "Mua88");
//
//        Email toEmail = new Email(to);
//
//        String subject = "Xác thực tài khoản";
//
//        // Generate secret code and save to db
//        String secretCode = UUID.randomUUID().toString();
//        log.info("secretCode = {}", secretCode);
//
//        // TOD0 save secretCode to db
//
//        // Tạo dynamic template data
//        Map<String, String> dynamicTemplateData = new HashMap<>();
//        dynamicTemplateData.put("name", username);
//        dynamicTemplateData.put("verification_link", verificationLink + "?secretCode=" + secretCode);
//
//        Mail mail = new Mail();
//        mail.setFrom(fromEmail);
//        mail.setSubject(subject);
//        Personalization personalization = new Personalization();
//        personalization.addTo(toEmail);
//
//        // Add dynamic template data
//        dynamicTemplateData.forEach(personalization::addDynamicTemplateData);
//
//        mail.addPersonalization(personalization);
//        mail.setTemplateId(templateId); // Template ID từ SendGrid
//
//        Request request = new Request();
//        request.setMethod(Method.POST);
//        request.setEndpoint("mail/send");
//        request.setBody(mail.build());
//        Response response = sendGrid.api(request);
//        if (response.getStatusCode() == 202) {
//            log.info("Verification sent successfully");
//        } else {
//            log.error("Verification sent failed");
//        }
//    }

    @PostAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Object sendVerificationEmail(String userId, UserChangePasswordRequest request) throws IOException {
            // Lấy thông tin người dùng từ cơ sở dữ liệu
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            String to = user.getEmail();
            log.info("Sending verification email for name={}", user.getUsername());

            // Tạo email
            Email fromEmail = new Email(from, "Mua88");
            Email toEmail = new Email(to);
            String subject = "Xác thực tài khoản";

            // Sinh OTP
            int otp = otpGenerate();

            // Tạo đối tượng ForgotPassword
            ForgotPassword fp = ForgotPassword.builder()
                    .otp(otp)
                    .expiryTime(new Date(System.currentTimeMillis() + 70 * 10000))
                    .user_id(user)
                    .build();

            // Thêm dữ liệu động vào email template
            Map<String, String> dynamicTemplateData = new HashMap<>();
            dynamicTemplateData.put("name", user.getUsername());
            dynamicTemplateData.put("otp", String.valueOf(otp));

            // Tạo đối tượng Mail
            Mail mail = new Mail();
            mail.setFrom(fromEmail);
            mail.setSubject(subject);

            // Thêm personalization
            Personalization personalization = new Personalization();
            personalization.addTo(toEmail);
            dynamicTemplateData.forEach(personalization::addDynamicTemplateData);
            mail.addPersonalization(personalization);

            mail.setTemplateId(templateId); // Template ID từ SendGrid

            // Gửi email qua SendGrid
            Request requestMail = new Request();
            requestMail.setMethod(Method.POST);
            requestMail.setEndpoint("mail/send");
            requestMail.setBody(mail.build());
            Response response = sendGrid.api(requestMail);

            // Kiểm tra kết quả gửi email
            log.info("Response status: {}", response.getStatusCode());
            log.info("Response body: {}", response.getBody());

            if (response.getStatusCode() == 202) {
                log.info("Verification sent successfully");
            } else {
                log.error("Verification sent failed with status code: {}", response.getStatusCode());
                log.error("Response body: {}", response.getBody());
            }

            // Lưu đối tượng ForgotPassword
            forgotPasswordRepository.save(fp);
            return true;
    }


    private Integer otpGenerate() {
        Random rand = new Random();
        return rand.nextInt(100_000, 999_999);
    }


}
