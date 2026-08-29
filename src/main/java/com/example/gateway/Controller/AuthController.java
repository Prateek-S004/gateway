package com.example.gateway.Controller;

import com.example.gateway.Dto.LoginRequestDto;
import com.example.gateway.Dto.LoginResponseDto;
import com.example.gateway.Dto.RefreshTokenRequestDto;
import com.example.gateway.Dto.RegisterRequestDto;
import com.example.gateway.Repository.UserRepository;
import com.example.gateway.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/register")
    public void registerUser(@RequestBody RegisterRequestDto register){
        authService.registerUser(register);
    }

    @PostMapping("/auth/login")
    public LoginResponseDto loginUser(@RequestBody LoginRequestDto loginRequestDto){
        return authService.login(loginRequestDto);
    }

    @PostMapping("/auth/refresh")
    public LoginResponseDto refreshToken(
            @RequestBody RefreshTokenRequestDto request) {

        return authService.refreshToken(request.getRefreshToken());
    }

    @PostMapping("/auth/logout")
    public void logout(Authentication authentication) {
        authService.logout(authentication);
    }
}
