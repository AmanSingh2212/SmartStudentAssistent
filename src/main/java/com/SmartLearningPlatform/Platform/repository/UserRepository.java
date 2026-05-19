package com.SmartLearningPlatform.Platform.repository;

import com.SmartLearningPlatform.Platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

     User findByEmail(String email);

}
