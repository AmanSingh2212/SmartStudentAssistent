package com.SmartLearningPlatform.Platform.repository;

import com.SmartLearningPlatform.Platform.entity.ForgotPasswordOtp;
import com.SmartLearningPlatform.Platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordOtp, Long> {

       ForgotPasswordOtp findByUser(User user);

}
