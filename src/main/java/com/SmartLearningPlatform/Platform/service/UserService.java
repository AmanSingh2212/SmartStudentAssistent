package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.User;
import com.SmartLearningPlatform.Platform.request.UpdateUserRequest;

import java.time.LocalDateTime;

public interface UserService {

    String generateForgotPasswordOtp() throws Exception;

    boolean isForgotPasswordOtpValid(String inputOtp, String storedOtp, LocalDateTime expiryTime) throws Exception;

    User findByJwtToken(String jwt) throws Exception;

    User findById(Long id) throws Exception;

    User updateUser(UpdateUserRequest updateUserRequest, Long id) throws Exception;

    void deleteUserById(Long id) throws Exception;

}
