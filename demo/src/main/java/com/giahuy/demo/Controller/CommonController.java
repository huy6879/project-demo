package com.giahuy.demo.Controller;


import com.giahuy.demo.Service.MailService;
import com.giahuy.demo.dto.response.ApiResponse;
import com.giahuy.demo.dto.response.MailResponse;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonController {

    MailService mailService;

    @PostMapping("/send-mail")
    ApiResponse<MailResponse> sendMail(@RequestParam String recipients, @RequestParam String subject, @RequestParam String content, @RequestParam(required = false) MultipartFile[] files) throws MessagingException {
        MailResponse MailResponse = mailService.sendMail(recipients, subject, content, files);
        return ApiResponse.<MailResponse>builder()
                .message(MailResponse.getResult())
                .build();
    }

}
