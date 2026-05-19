package com.SmartLearningPlatform.Platform.service;

import com.SmartLearningPlatform.Platform.config.JwtProvider;
import com.SmartLearningPlatform.Platform.entity.User;
import com.SmartLearningPlatform.Platform.repository.UserRepository;
import com.SmartLearningPlatform.Platform.request.UpdateUserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public User findById(Long id) throws Exception {

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty())
        {
            throw new Exception("User not present with id" + id);
        }

        return userOptional.get();

    }

    @Override
    public User updateUser(UpdateUserRequest updateUserRequest, Long id) throws Exception {

        User user = findById(id);

        if (updateUserRequest.getName() != null) {
            user.setName(updateUserRequest.getName());
        }

        if (updateUserRequest.getEmail() != null) {
            user.setEmail(updateUserRequest.getEmail());
        }

        if (updateUserRequest.getPassword() != null) {
            // Encode the password before saving (assumes a PasswordEncoder bean is injected)
            user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }

        if (updateUserRequest.getMobile() != null) {
            user.setMobile(updateUserRequest.getMobile());
        }

        if (updateUserRequest.getStandard() != null) {
            user.setStandard(updateUserRequest.getStandard());
        }

        if (updateUserRequest.getBoard() != null) {
            user.setBoard(updateUserRequest.getBoard());
        }

        if (updateUserRequest.getProfileImage() != null) {
            user.setProfileImage(updateUserRequest.getProfileImage());
        }

        if (updateUserRequest.getTargetExam() != null) {
            user.setTargetExam(updateUserRequest.getTargetExam());
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) throws Exception {

        User user = findById(id);

        userRepository.delete(user);

    }

}
