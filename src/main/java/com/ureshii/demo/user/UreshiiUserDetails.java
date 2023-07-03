package com.ureshii.demo.user;

import com.ureshii.demo.role.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UreshiiUserDetails implements UserDetails {

    private final UreshiiUser ureshiiUser;

    public UreshiiUserDetails(UreshiiUser ureshiiUser) {
        this.ureshiiUser = ureshiiUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = ureshiiUser.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return ureshiiUser.getPassword();
    }

    @Override
    public String getUsername() {
        return ureshiiUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ureshiiUser.isEnabled();
    }

    public UreshiiUser getUser() {
        return ureshiiUser;
    }
}
