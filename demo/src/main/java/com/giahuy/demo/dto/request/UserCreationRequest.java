package com.giahuy.demo.dto.request;

import com.giahuy.demo.validator.DobConstraint;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;
    @Size(min = 6, message = "PASSWORD_INVALID")
    String password;
    String firstName;
    String lastName;

    @Email(message = "INVALID_EMAIL_FORMAT")
    String email;

    @Nullable // Cho phép file là null
    MultipartFile file;

    @DobConstraint(min = 5, message = "INVALID_DOB")
    LocalDate dob;
}
