package com.example.myPractice1.appUser;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum AppUserRole {
    
    USER(Sets.newHashSet(AppUserPermission.USER_READ, AppUserPermission.USER_WRITE)), 
    ADMIN(Sets.newHashSet(AppUserPermission.ADMIN_READ, AppUserPermission.ADMIN_WRITE)), 
    TRAINEE(Sets.newHashSet(AppUserPermission.TRAINEE_READ, AppUserPermission.TRAINEE_WRITE));


    private Set<AppUserPermission> permissions;


    public Set<SimpleGrantedAuthority> getPermissions() {
        // converting all permissions from parameters into SimpleGrantedAuthorities
        Set<SimpleGrantedAuthority> permissions = this.permissions.stream()
                                                                  .map(permission -> new SimpleGrantedAuthority(permission.name()))
                                                                  .collect(Collectors.toSet());

        // adding role as SimpleGrantedAuthority
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }
}