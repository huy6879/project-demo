package com.giahuy.demo.dto.request;

import jakarta.annotation.Nullable;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailRequest {

    String recipients;
    String subject;
    String content;
    @Nullable // Cho phép file là null
    MultipartFile[] files;

}
