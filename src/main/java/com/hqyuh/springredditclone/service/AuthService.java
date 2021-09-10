package com.hqyuh.springredditclone.service;

import com.hqyuh.springredditclone.dto.RegisterRequest;
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


    @Transactional
    public void signup(RegisterRequest registerRequest){

        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getPassword());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);

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
