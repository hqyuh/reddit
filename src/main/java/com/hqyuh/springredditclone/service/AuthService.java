package com.hqyuh.springredditclone.service;

import com.hqyuh.springredditclone.dto.AuthenticationResponse;
import com.hqyuh.springredditclone.dto.LoginRequest;
import com.hqyuh.springredditclone.dto.RefreshTokenRequest;
import com.hqyuh.springredditclone.dto.RegisterRequest;
import com.hqyuh.springredditclone.exception.SpringRedditException;
import com.hqyuh.springredditclone.model.NotificationEmail;
import com.hqyuh.springredditclone.model.User;
import com.hqyuh.springredditclone.model.VerificationToken;
import com.hqyuh.springredditclone.repository.UserRepository;
import com.hqyuh.springredditclone.repository.VerificationTokenRepository;
import com.hqyuh.springredditclone.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;


    @Transactional
    public void signup(RegisterRequest registerRequest){

        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(
                new NotificationEmail("Please activate your Account",
                        user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                             "please click on the below url to activate your account: \n" +
                             "http://localhost:8080/api/auth/accountVerification/" + token)
        );

    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    // h??m t???o token trong b???ng VerificationToken
    private String generateVerificationToken(User user){

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    // h??m t??m token
    public void verifyAccount(String token){

        Optional<VerificationToken> verificationToken =
                verificationTokenRepository.findByToken(token);
        // m?? kh??ng h???p l???
        verificationToken
                .orElseThrow(() -> new SpringRedditException("Invalid Token"));

        // ph???i t??m user(id) ????? enabled
        fetchUserAndEnable(verificationToken.get());

    }

    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken){

        Long userId = verificationToken.getUser().getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SpringRedditException("User not found with id " + userId));

        // set enabled
        user.setEnabled(true);

        userRepository.save(user);

    }

    public AuthenticationResponse login(LoginRequest loginRequest){

        // l???y ra username v?? password
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        // t???o jwt
        String token = jwtProvider.generateToken(authenticate);

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    // refreshToken
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        // t???o l???i jwt
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());

        return AuthenticationResponse.builder()
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public User getCurrentUser(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUserName(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found " + principal.getUsername()));
    }


}
