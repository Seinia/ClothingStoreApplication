package com.fix.OnlineClothingStore.Controller;


import com.fix.OnlineClothingStore.Model.ResponseMessageEntity;
import com.fix.OnlineClothingStore.Model.User;
import com.fix.OnlineClothingStore.Repo.UserRepository;
import com.fix.OnlineClothingStore.Service.AuthenticationService;
import com.fix.OnlineClothingStore.Service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @Autowired
    private UserRepository repo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("register")
    public ResponseEntity<ResponseMessageEntity> register(@RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody User user) {
        return service.authenticate(user);
    }

    @PostMapping("refresh-token")
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return service.refreshToken(request, response);
    }
}