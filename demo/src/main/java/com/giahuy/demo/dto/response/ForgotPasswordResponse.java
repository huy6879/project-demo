package com.giahuy.demo.dto.response;


import com.giahuy.demo.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgotPasswordResponse {

    Integer otp;

    Date expiryTime;

    User user_id;


}
