package com.giahuy.demo.Service;

import  com.giahuy.demo.constant.PredefinedRole;
import com.giahuy.demo.dto.request.UserCreationRequest;
import com.giahuy.demo.dto.request.UserUpdateRequest;
import com.giahuy.demo.dto.response.ForgotPasswordResponse;
import com.giahuy.demo.dto.response.UserResponse;
import com.giahuy.demo.entity.ForgotPassword;
import com.giahuy.demo.entity.Role;
import com.giahuy.demo.entity.User;
import com.giahuy.demo.exception.AppException;
import com.giahuy.demo.exception.ErrorCode;
import com.giahuy.demo.mapper.UserMapper;
import com.giahuy.demo.repository.ForgotPasswordRepository;
import com.giahuy.demo.repository.RoleRepository;
import com.giahuy.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    CloudinaryService cloudinaryService;

    AuthenticationService authenticationService;
    private final ForgotPasswordRepository forgotPasswordRepository;

    public UserResponse createUser(UserCreationRequest request) {

        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        if(userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        User u = userMapper.toUser(request);

        u.setPassword(passwordEncoder.encode(u.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        u.setRoles(roles);

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            Map res = cloudinaryService.uploadImage(request.getFile());
            u.setImage(res.get("secure_url").toString());
        } else {
            u.setImage(null);
        }

        return userMapper.toUserResponse(userRepository.save(u));

    }

    @PostAuthorize("hasRole('ADMIN') or (returnObject.username == authentication.name)")
    public UserResponse updateUser(String userId, UserUpdateRequest request){

        Integer enteredOtp = Integer.valueOf(request.getOtp());

        List<ForgotPassword> fpList = forgotPasswordRepository.findOTPByUserId(userId);

        List<ForgotPasswordResponse> fpResponseList = fpList.stream()
                .map(fp -> new ForgotPasswordResponse(
                        fp.getOtp(), fp.getExpiryTime(), fp.getUser_id() // assuming constructor of ForgotPasswordResponse matches
                ))
                .collect(Collectors.toList());

        List<ForgotPasswordResponse> validOtps = fpResponseList.stream()
                .filter(fp -> fp.getExpiryTime().after(new Date()))  // Lọc các OTP còn hiệu lực
                .collect(Collectors.toList());  // Thu thập tất cả OTP hợp lệ vào một danh sách


        if (validOtps.isEmpty()) {
                throw new AppException(ErrorCode.INVALID_OTP);
            }
        boolean isOtpValid = validOtps.stream()
                .anyMatch(fp -> fp.getOtp().equals(enteredOtp));

        if (!isOtpValid) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }

        User user = userRepository.findById(userId).
                orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            Map res = cloudinaryService.uploadImage(request.getFile());
            user.setImage(res.get("secure_url").toString());
        } else {
            user.setImage(null);
        }

        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

//    @PostAuthorize("hasRole('ADMIN') or (returnObject.username == authentication.name)")
//    public UserResponse changePassword(String userId, UserChangePasswordRequest request) {
//
//        User user = userRepository.findById(userId).
//                orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
//
//        int
//
//    }
//
//    private Integer otpGenerate() {
//        Random rand = new Random();
//        return rand.nextInt(100_000, 999_999);
//    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById((userId));
    }


    @PostAuthorize("hasRole('ADMIN') or (returnObject.username == authentication.name)")
    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();

        String name = context.getAuthentication().getName();
        log.info(name);

        User user = userRepository.findByUsername(name)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }



    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("hasRole('ADMIN')")
    public UserResponse getUserById(String id){
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User Not Found")));
    }


}
