//package com.ashu.counsellingbackend.controller;
//
//
//
//import com.ashu.counsellingbackend.dto.LoginUserDto;
//import com.ashu.counsellingbackend.dto.RegisterUserDto;
//import com.ashu.counsellingbackend.dto.VerifyUserDto;
//import com.ashu.counsellingbackend.model.User;
//import com.ashu.counsellingbackend.response.LoginResponse;
//import com.ashu.counsellingbackend.service.AuthenticationService;
//import com.ashu.counsellingbackend.service.JwtService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = "http://localhost:5173")
//@RequestMapping("/auth")
//@RestController
//public class AuthenticationController {
//    private final JwtService jwtService;
//
//    private final AuthenticationService authenticationService;
//
//    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
//        this.jwtService = jwtService;
//        this.authenticationService = authenticationService;
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
//        User registeredUser = authenticationService.signup(registerUserDto);
//        return ResponseEntity.ok(registeredUser);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto){
//        User authenticatedUser = authenticationService.authenticate(loginUserDto);
//        String jwtToken = jwtService.generateToken(authenticatedUser);
//        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
//        return ResponseEntity.ok(loginResponse);
//    }
//
//    @PostMapping("/verify")
//    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
//        try {
//            authenticationService.verifyUser(verifyUserDto);
//            return ResponseEntity.ok("Account verified successfully");
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PostMapping("/resend")
//    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
//        try {
//            authenticationService.resendVerificationCode(email);
//            return ResponseEntity.ok("Verification code sent");
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//}


package com.ashu.counsellingbackend.controller;

import com.ashu.counsellingbackend.dto.LoginUserDto;
import com.ashu.counsellingbackend.dto.RegisterUserDto;
import com.ashu.counsellingbackend.dto.VerifyUserDto;
import com.ashu.counsellingbackend.model.User;
import com.ashu.counsellingbackend.response.LoginResponse;
import com.ashu.counsellingbackend.service.AuthenticationService;
import com.ashu.counsellingbackend.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User registeredUser = authenticationService.signup(registerUserDto);
            log.info("User registration successful for email: {}", registerUserDto.getEmail());
            return ResponseEntity.ok(registeredUser);
        } catch (Exception ex) {
            log.error("Error during user registration for email {}: {}", registerUserDto.getEmail(), ex.getMessage());
            return ResponseEntity.status(500).body("Registration failed: " + ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken(authenticatedUser);
            LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
            log.info("User login successful for email: {}", loginUserDto.getEmail());
            return ResponseEntity.ok(loginResponse);
        } catch (Exception ex) {
            log.error("Login failed for email {}: {}", loginUserDto.getEmail(), ex.getMessage());
            return ResponseEntity.status(401).body("Authentication failed: " + ex.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);
            log.info("User verification successful for email: {}", verifyUserDto.getEmail());
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e) {
            log.error("User verification failed for email {}: {}", verifyUserDto.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body("Verification failed: " + e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            log.info("Verification code resent to email: {}", email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            log.error("Failed to resend verification code to email {}: {}", email, e.getMessage());
            return ResponseEntity.badRequest().body("Failed to resend verification code: " + e.getMessage());
        }
    }
}
