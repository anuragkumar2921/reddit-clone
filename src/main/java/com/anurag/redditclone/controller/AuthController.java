package com.anurag.redditclone.controller;

import com.anurag.redditclone.dto.AuthenticationRequest;
import com.anurag.redditclone.dto.AuthenticationResponse;
import com.anurag.redditclone.dto.RegisterRequestDto;
import com.anurag.redditclone.repository.UserRepository;
import com.anurag.redditclone.repository.VerificationTokenRepository;
import com.anurag.redditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by anurag on 1/6/20.
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequestDto registerRequestDto) {
        authService.signUp(registerRequestDto);
        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }

    @PostMapping("/accountVerification/{tokenId}")
    public ResponseEntity<String> accountVerification(@PathVariable String tokenId) {
        authService.verifyToken(tokenId);
        return new ResponseEntity<>("User account successfully activated", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        return authService.login(authenticationRequest);
    }
}
