package com.fix.OnlineClothingStore.Service;

import com.fix.OnlineClothingStore.Model.Token;
import com.fix.OnlineClothingStore.Repo.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
public class LogoutService implements LogoutHandler {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
        tokenRepository.findByAccessToken(token).ifPresent(t -> {
            tokenRepository.deleteAllTokensByUser(t.getUser().getId());
        });

        SecurityContextHolder.clearContext();
    }

}
