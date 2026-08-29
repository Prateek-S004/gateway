package com.example.gateway.Service;

import com.example.gateway.Config.JwtUtil;
import com.example.gateway.Dto.LoginRequestDto;
import com.example.gateway.Dto.LoginResponseDto;
import com.example.gateway.Dto.RegisterRequestDto;
import com.example.gateway.Entity.Role.Role;
import com.example.gateway.Entity.User;
import com.example.gateway.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public void registerUser(RegisterRequestDto register) {

        User existingUser =
                (User) userRepository.findByEmail(register.getEmail());

        if (existingUser!=null) {
            throw new IllegalArgumentException(
                    "Email already registered"
            );
        }

        String hashedPassword =
                passwordEncoder.encode(register.getPassword());

        User user = new User();

        user.setName(register.getName());
        user.setEmail(register.getEmail());
        user.setPassword(hashedPassword);

        user.setRole(Role.USER);
        user.setEnabled(true);

        userRepository.save(user);
    }

    private final JwtUtil jwtUtil;
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));


        User user = (User) authentication.getPrincipal();

        String token = jwtUtil.generateAccessToken(user);

        String rtoken = jwtUtil.generateRefreshToken(user);

        System.out.println("Authenticated User: " + user.getName());

        user.setRefreshToken(rtoken);

        userRepository.save(user);

        return new LoginResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                token,
                rtoken
        );
    }


    public LoginResponseDto refreshToken(String refreshToken) {
        String username = jwtUtil.getUsername(refreshToken);
        User user = userRepository.findByName(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        if(!user.getRefreshToken().equals(refreshToken)){
            throw new IllegalArgumentException("Invalid Refresh token");
        }

        String newAccesstoken = jwtUtil.generateAccessToken(user);

        return new LoginResponseDto(user.getId(),user.getName(),user.getEmail(),user.getRole(),newAccesstoken,user.getRefreshToken());
    }

    public void logout(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        user.setRefreshToken(null);

        userRepository.save(user);
    }
}