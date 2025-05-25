package com.fix.OnlineClothingStore.filter;



import com.fix.OnlineClothingStore.Model.MyUserDetails;
import com.fix.OnlineClothingStore.Model.Token;
import com.fix.OnlineClothingStore.Model.User;
import com.fix.OnlineClothingStore.Repo.TokenRepository;
import com.fix.OnlineClothingStore.Repo.UserRepository;
import com.fix.OnlineClothingStore.Service.JwtService;
import com.fix.OnlineClothingStore.Service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    ApplicationContext context;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            userName = jwtService.extractUsername(token);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = context.getBean(UserRepository.class).findByUsername(userName).orElse(null);

            if (user != null) {
                Token storedToken = context.getBean(TokenRepository.class).findByUserId(user.getId()).orElse(null);

                if (storedToken != null) {
                    if (jwtService.isTokenExpired(token) && jwtService.isValidRefreshToken(storedToken.getRefreshToken(), user)) {
                        String newAccessToken = jwtService.generateAccessToken(user);
                        storedToken.setAccessToken(newAccessToken);
                        context.getBean(TokenRepository.class).save(storedToken);

                        response.setHeader("Authorization", "Bearer " + newAccessToken);
                    }

                    if (jwtService.validateToken(token, new MyUserDetails(user))) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                new MyUserDetails(user), null, new MyUserDetails(user).getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}