package com.example.myPractice1.appUser;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class AppUser implements UserDetails {
    @Unique
    protected String userName; // has to be a valid email
    protected String password;
    protected String firstName;
    protected String lastName;
    @DateTimeFormat
    protected LocalDate birthday;
    @Enumerated(EnumType.STRING)
    protected AppUserRole role;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = false;
    
    
    public AppUser(String userName, 
                   String password, 
                   String firstName, 
                   String lastName, 
                   LocalDate birthday, 
                   AppUserRole role) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.role = (role != null) ? role : AppUserRole.USER; // using 'USER' as default role
    }


    public boolean checkFields() {

        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getPermissions();
    }


    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public String getUsername() {
        return this.userName;
    }


    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }


    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }


    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}