package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.config.JwtProvider;
import com.SmartLearningPlatform.Platform.entity.User;
import com.SmartLearningPlatform.Platform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String generateForgotPasswordOtp() throws Exception {
        return String.format("%06d", new Random().nextInt(999999));

    }

    @Override
    public boolean isForgotPasswordOtpValid(String inputOtp, String storedOtp, LocalDateTime expiryTime) throws Exception {
        return inputOtp.equals(storedOtp) && LocalDateTime.now().isBefore(expiryTime);
    }

    @Override
    public User findByJwtToken(String jwt) throws Exception {

        String email = JwtProvider.getEmailFromToken(jwt);

        User user = userRepository.findByEmail(email);

        return user;

    }

}
