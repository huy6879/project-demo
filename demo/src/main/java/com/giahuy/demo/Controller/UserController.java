package com.giahuy.demo.Controller;


import com.giahuy.demo.Service.EmailService;
import com.giahuy.demo.Service.UserService;
import com.giahuy.demo.dto.request.UserChangePasswordRequest;
import com.giahuy.demo.dto.response.ApiResponse;
import com.giahuy.demo.dto.request.UserCreationRequest;
import com.giahuy.demo.dto.request.UserUpdateRequest;
import com.giahuy.demo.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    private final EmailService emailService;

    @PostMapping()
    ApiResponse<UserResponse> createUser(@ModelAttribute @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }


    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
//        var authentication =  SecurityContextHolder.getContext().getAuthentication();
//
//        log.info("Username : {}", authentication.getName());
//
//        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }


    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable String userId,
                                         @ModelAttribute @Valid  UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @PostMapping("/change-password/{userId}")
    ApiResponse<?> changePassword(@PathVariable String userId,
                                  @RequestBody @Valid UserChangePasswordRequest request) throws IOException {
        return ApiResponse.builder()
                .result(emailService.sendVerificationEmail(userId, request))
                .build();


    }

    @DeleteMapping("{userId}")
    String deleteUser(@PathVariable("userId") String userId){
        userService.deleteUser(userId);
        return  "User has been deleted";
    }
}
