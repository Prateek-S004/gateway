package com.example.gateway.Entity.Role;

import lombok.Getter;

import java.util.Set;

@Getter
public enum Role {

    USER(
            Permission.ORDER_READ,
            Permission.ORDER_CREATE,
            Permission.ORDER_CANCEL,
            Permission.FILE_UPLOAD
    ),

    MANAGER(
            Permission.USER_READ,
            Permission.ORDER_READ,
            Permission.ORDER_CANCEL
    ),

    ADMIN(Permission.findAll());

    private final Set<Permission> permissions;

    Role(Permission... permissions){
        this.permissions = Set.of(permissions);
    }

    Role(Set<Permission> permissions){
        this.permissions = Set.copyOf(permissions);
    }
}
