package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.entity.User;

import java.time.LocalDateTime;

public interface UserService {

    String generateForgotPasswordOtp() throws Exception;

    boolean isForgotPasswordOtpValid(String inputOtp, String storedOtp, LocalDateTime expiryTime) throws Exception;

    User findByJwtToken(String jwt) throws Exception;

}
