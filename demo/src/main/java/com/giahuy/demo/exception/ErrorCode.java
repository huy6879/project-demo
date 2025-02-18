package com.giahuy.demo.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {

//    UNCATEGORIED_EXCEPTION(9999, "Uncategoried Error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not exist", HttpStatus.NOT_FOUND),
    PASSWORD_WRONG(1011, "Password is not correct", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    PROD_EXISTED(1009, "Product existed", HttpStatus.BAD_REQUEST),
    CATE_NOT_EXISTED(1010, "Category not existed", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1011, "Invalid token", HttpStatus.UNAUTHORIZED),
    INVALID_EMAIL_FORMAT(1012, "Invalid email format", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1013, "Email existed", HttpStatus.BAD_REQUEST),
    INVALID_OTP(1014, "Invalid otp", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED(1015, "OTP expired", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    int code;
    String message;
    HttpStatusCode statusCode;

}
