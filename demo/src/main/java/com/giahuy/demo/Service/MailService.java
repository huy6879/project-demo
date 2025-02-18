package com.giahuy.demo.Service;

import com.giahuy.demo.dto.response.MailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailService {

    JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String emailFrom;

    public MailService(JavaMailSender mailSender, @Value("${spring.mail.from}") String emailFrom) {
        this.mailSender = mailSender;
        this.emailFrom = emailFrom;
    }


    public MailResponse sendMail(String recipients, String subject, String content, MultipartFile[] files) throws MessagingException {

        log.info("Sending......");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true, "UTF-8");
        helper.setFrom(emailFrom);

        if(recipients.contains(","))
            helper.setTo(InternetAddress.parse(recipients));
        else
            helper.setTo(recipients);

        if(files != null){
            for(MultipartFile file : files){
                helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()),file);
            }
        }

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
        return MailResponse
                .builder()
                .recipients(recipients)
                .result("Send Success")
                .build();

    }

}
