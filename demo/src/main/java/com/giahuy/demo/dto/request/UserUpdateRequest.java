package com.giahuy.demo.dto.request;

import com.giahuy.demo.validator.DobConstraint;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
     String username;
     @Size(min = 5, message = "PASSWORD_INVALID")
     String password;
     String firstName;
     String lastName;
     String otp;


     @DobConstraint(min = 2, message = "INVALID_DOB")
     LocalDate dob;

     @Nullable // Cho phép file là null
     MultipartFile file;

     @Nullable // Cho phép role là null
     List<String> roles;
}
