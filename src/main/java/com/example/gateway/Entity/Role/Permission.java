package com.example.gateway.Entity.Role;

import java.util.EnumSet;
import java.util.Set;

public enum Permission {

    USER_READ,
    USER_WRITE,
    USER_DELETE,
    ORDER_READ,
    ORDER_CREATE,
    ORDER_CANCEL,
    FILE_READ,
    FILE_UPLOAD,
    FILE_DELETE;

    public static Set<Permission> findAll() {
        return EnumSet.allOf(Permission.class);
    }
}
