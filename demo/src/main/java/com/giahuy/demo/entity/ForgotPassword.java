package com.giahuy.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ForgotPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    Integer fpid;

    @Column(nullable = false)
    Integer otp;

    @Column(nullable = false)
    Date expiryTime;

    @ManyToOne
    @JoinColumn(name = "user_id")  // Sử dụng @ManyToOne để mỗi người dùng có thể có nhiều mã OTP
    User user_id;
}
