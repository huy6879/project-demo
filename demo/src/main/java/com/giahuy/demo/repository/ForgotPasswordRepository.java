package com.giahuy.demo.repository;

import com.giahuy.demo.dto.response.ForgotPasswordResponse;
import com.giahuy.demo.entity.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {


    @Query("FROM ForgotPassword fp WHERE fp.user_id.id = :id AND fp.expiryTime >= CURRENT_TIMESTAMP")
    List<ForgotPassword> findOTPByUserId(String id);


}
