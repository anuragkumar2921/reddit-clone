package com.anurag.redditclone.service;

import com.anurag.redditclone.dto.RegisterRequestDto;
import com.anurag.redditclone.exception.RedditCloneException;
import com.anurag.redditclone.model.NotificationEmail;
import com.anurag.redditclone.model.User;
import com.anurag.redditclone.model.VerificationToken;
import com.anurag.redditclone.repository.UserRepository;
import com.anurag.redditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by anurag on 1/6/20.
 */
@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private static final String ACTIVATION_EMAIL_SUBJECT = "Activation Email";
    private static final String ACTIVATION_MESSAGE = "Thank you for signing up to Spring Reddit, please click on the below url to activate your account";
    private static final String REDIRECTING_URL = "http://localhost:8080/api/auth/accountVerification/";
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final MailService mailService;

    @Transactional
    public String signUp(RegisterRequestDto registerRequestDto) {
        User user = new User();
        user.setEmail(registerRequestDto.getEmail());
        user.setEnabled(false);
        user.setUserName(registerRequestDto.getUserName());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setCreatedDate(Instant.now());
        userRepository.save(user);
        String tokenId = generateVerificationToken(user);
        mailService.sendEmail(new NotificationEmail(ACTIVATION_EMAIL_SUBJECT, user.getEmail(), ACTIVATION_MESSAGE + REDIRECTING_URL + tokenId));
        return tokenId;
    }

    private String generateVerificationToken(User user) {
        String tokenId = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(tokenId);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Instant.now());
        verificationTokenRepository.save(verificationToken);
        return tokenId;
    }

    public void verifyToken(String tokenId) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(tokenId);
        verificationToken.orElseThrow(() -> new RedditCloneException("invalid token !"));
        fetchUserAndEnable(verificationToken.get());
        log.info("User account successfully activated !");
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String userName = verificationToken.getUser().getUserName();
        User user = userRepository.findByUserName(userName).orElseThrow(()-> new RedditCloneException("User not found for the userName : " + userName));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
