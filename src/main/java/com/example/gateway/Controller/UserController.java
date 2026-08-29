package com.example.gateway.Controller;

import com.example.gateway.Dto.LoginResponseDto;
import com.example.gateway.Dto.RefreshTokenRequestDto;
import com.example.gateway.Dto.UpdateUserDto;
import com.example.gateway.Dto.UserResponseDto;
import com.example.gateway.Entity.User;
import com.example.gateway.Repository.UserRepository;
import com.example.gateway.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    
    @GetMapping("/users")
    public List<UserResponseDto> getUser(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/user")
    public UserResponseDto getUserDetails(Authentication authentication){
        return userService.getCurrentUserDetails(authentication);
    }

    @PreAuthorize("hasAuthority('USER_WRITE')")
    @PutMapping("/users/{id}")
    public UserResponseDto updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserDto updateUserDto) {

        return userService.updateUser(id, updateUserDto);
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("/users/test-permission")
    public String testPermission() {
        return "You have USER_READ permission";
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("/users/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

}
