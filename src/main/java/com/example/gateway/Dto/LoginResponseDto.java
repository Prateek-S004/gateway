package com.example.gateway.Dto;

import com.example.gateway.Entity.Role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String token;
    private String refreshToken;
}
