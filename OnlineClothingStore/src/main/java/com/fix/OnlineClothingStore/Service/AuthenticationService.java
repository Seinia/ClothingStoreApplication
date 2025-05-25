package com.fix.OnlineClothingStore.Service;

import com.fix.OnlineClothingStore.Model.AuthenticationResponse;
import com.fix.OnlineClothingStore.Model.ResponseMessageEntity;
import com.fix.OnlineClothingStore.Model.Token;
import com.fix.OnlineClothingStore.Model.User;
import com.fix.OnlineClothingStore.Repo.TokenRepository;
import com.fix.OnlineClothingStore.Repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtService jwtService;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<ResponseMessageEntity> register(User user){

        if(repo.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ResponseMessageEntity("User is already registered"));
        }

        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(accessToken, refreshToken, user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessageEntity("User is registered successfully"));
    }

    public ResponseEntity authenticate(User request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseMessageEntity("Invalid email or password provided"));
        }

        User user = repo.findByUsername(request.getUsername()).orElseThrow();

        Token existingToken = tokenRepository.findByUserId(user.getId()).orElse(null);

        if (existingToken != null) {
            return ResponseEntity.ok(
                    new AuthenticationResponse(existingToken.getAccessToken(), existingToken.getRefreshToken()));
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(accessToken, refreshToken, user);

        return ResponseEntity.ok(
                new AuthenticationResponse(accessToken, refreshToken));
    }


    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = tokenRepository.findByUserId(user.getId()).orElse(new Token());
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setUser(user);
        tokenRepository.save(token);
    }

    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        User user = repo.findByUsername(username)
                .orElseThrow(()->new RuntimeException("No user found"));


        if(jwtService.isValidRefreshToken(token, user)) {

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity(new AuthenticationResponse(accessToken, refreshToken), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }
}
