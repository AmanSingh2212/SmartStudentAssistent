package com.SmartLearningPlatform.Platform.service;

public interface EmailService {

    public void sendOtpEmail(String to, String otp) throws Exception;

}
