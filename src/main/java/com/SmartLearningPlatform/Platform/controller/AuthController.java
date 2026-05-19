package com.SmartLearningPlatform.Platform.controller;


import com.SmartLearningPlatform.Platform.config.JwtProvider;
import com.SmartLearningPlatform.Platform.datatypes.ROLE;
import com.SmartLearningPlatform.Platform.entity.ForgotPasswordOtp;
import com.SmartLearningPlatform.Platform.entity.User;
import com.SmartLearningPlatform.Platform.repository.ForgotPasswordRepository;
import com.SmartLearningPlatform.Platform.repository.UserRepository;
import com.SmartLearningPlatform.Platform.request.LoginRequest;
import com.SmartLearningPlatform.Platform.response.AuthResponse;
import com.SmartLearningPlatform.Platform.response.SignupResponse;
import com.SmartLearningPlatform.Platform.service.CustomUserService;
import com.SmartLearningPlatform.Platform.service.EmailService;
import com.SmartLearningPlatform.Platform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth/user")
public class AuthController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final CustomUserService customUserService;

    private final UserService userService;

    private final EmailService emailService;

    private final ForgotPasswordRepository forgotPasswordRepository;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
                          CustomUserService customUserService, UserService userService, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.customUserService = customUserService;
        this.userService = userService;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }


    @PostMapping("/add")
    private ResponseEntity<SignupResponse> addUser(@RequestBody User user) throws Exception{

        if(userRepository.findByEmail(user.getEmail()) != null)
        {
            throw new Exception("User already exists");
        }

        User user1 = new User();

        user1.setEmail(user.getEmail());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setName(user.getName());
        user1.setCreatedAt(LocalDateTime.now());
        user1.setMobile(user.getMobile());
        user1.setStandard(user.getStandard());
        user1.setBoard(user.getBoard());
        user1.setRole(user.getRole());
        user1.setProfileImage(user.getProfileImage());
        user1.setTargetExam(user.getTargetExam());


//          double bmi = userService.getBmiOfUser(user1.getWeight(), user.getHeight(), user1);

        userRepository.save(user1);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user1.getEmail(),
                user1.getPassword(),authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        SignupResponse authResponse1 = new SignupResponse();
        authResponse1.setJwt(token);
        authResponse1.setMessage("User added successfully");
        authResponse1.setRole(user1.getRole());

        return new ResponseEntity<>(authResponse1, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    private ResponseEntity<AuthResponse> getData(@RequestBody LoginRequest loginRequest,
                                                 HttpServletResponse response) {


        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println(username + " ----- " + password);

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User  user = userRepository.findByEmail(username);

        String token = jwtProvider.generateToken(authentication);
//        RefreshToken refreshToken = jwtProvider.createRefreshToken(user);

//        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken.getToken())
//                .httpOnly(true)
//                .secure(true)
//                .sameSite("Strict")
//                .path("/auth/users")
//                .maxAge(Duration.ofDays(30))
//                .build();

//        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login Success");
        authResponse.setJwt(token);
//        authResponse.setRefreshToken(refreshToken);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();


        String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();


        authResponse.setRole(ROLE.valueOf(roleName));

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserService.loadUserByUsername(username);

        System.out.println("sign in userDetails - " + userDetails);

        if (userDetails == null) {
            System.out.println("sign in userDetails - null " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("sign in userDetails - password not match " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String,String> payload) throws Exception {
        String email = payload.get("email");
        User user = userRepository.findByEmail(email);

        if(user == null)
        {
            throw new RuntimeException("User not found");
        }

        ForgotPasswordOtp forgotPasswordOtp = new ForgotPasswordOtp();
        forgotPasswordOtp.setOtp(userService.generateForgotPasswordOtp());
        forgotPasswordOtp.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        forgotPasswordOtp.setUser(user);

        emailService.sendOtpEmail(user.getEmail(), forgotPasswordOtp.getOtp());

        forgotPasswordRepository.save(forgotPasswordOtp);

        return new ResponseEntity<>("Otp Send to email", HttpStatus.OK);

    }

    @PostMapping("reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String,String> payload) throws Exception{

        String email = payload.get("email");
        String otp = payload.get("otp");
        String newPassword = payload.get("password");

        User user = userRepository.findByEmail(email);

        if(user == null)
        {
            throw new Exception("User not found with email");
        }

        ForgotPasswordOtp forgotPasswordOtp = forgotPasswordRepository.findByUser(user);

        String savedOtp = forgotPasswordOtp.getOtp();

        if(!userService.isForgotPasswordOtpValid(otp, savedOtp, forgotPasswordOtp.getOtpExpiry()))
        {
            throw new Exception("Invalid or Expired otp");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        forgotPasswordRepository.delete(forgotPasswordOtp);

        return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);

    }

}
