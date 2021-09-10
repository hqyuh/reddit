package com.hqyuh.springredditclone.service;

import com.hqyuh.springredditclone.dto.RegisterRequest;
import com.hqyuh.springredditclone.model.NotificationEmail;
import com.hqyuh.springredditclone.model.User;
import com.hqyuh.springredditclone.model.VerificationToken;
import com.hqyuh.springredditclone.repository.UserRepository;
import com.hqyuh.springredditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;


    @Transactional
    public void signup(RegisterRequest registerRequest){

        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(
                new NotificationEmail("Please activate your Account",
                        user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                             "please click on the below url to activate your account: " +
                             "http://localhost:8080/api/auth/accountVerification/" + token)
        );

    }

    // hàm tạo token trong bảng VerificationToken
    private String generateVerificationToken(User user){

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

}
