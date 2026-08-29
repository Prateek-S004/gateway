package com.example.gateway.Service;

import com.example.gateway.Config.JwtUtil;
import com.example.gateway.Dto.LoginResponseDto;
import com.example.gateway.Dto.UpdateUserDto;
import com.example.gateway.Dto.UserResponseDto;
import com.example.gateway.Entity.User;
import com.example.gateway.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserResponseDto getCurrentUserDetails(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if(user == null){
            throw new UsernameNotFoundException("user not found: " + authentication.getName());
        }
        return new UserResponseDto(user.getId(), user.getName(), user.getRole(), user.getEmail());

    }

    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAll()
                .stream().map(user -> new UserResponseDto(
                        user.getId(), user.getName(), user.getRole(), user.getEmail())).toList();
    }

    public UserResponseDto updateUser(Long id, UpdateUserDto updateUserDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(updateUserDto.getName());
        user.setEmail(updateUserDto.getEmail());

        userRepository.save(user);

        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getRole(),
                user.getEmail()
        );
    }

    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(id);
    }

    public UserResponseDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getRole(),
                user.getEmail()
        );
    }
}
