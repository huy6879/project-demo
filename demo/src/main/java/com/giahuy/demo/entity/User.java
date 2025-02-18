package com.giahuy.demo.entity;

import java.time.LocalDate;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    String username;
    String password;
    String firstName;
    LocalDate dob;
    String lastName;

    String email;

    @Nullable
    String image;

    @Nullable // Cho phép file là null
    @ManyToMany
    Set<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
    Set<Order> orders;

    @OneToMany(mappedBy = "user_id") // Mỗi người dùng có thể có nhiều mã OTP
    Set<ForgotPassword> forgotPasswords;

}