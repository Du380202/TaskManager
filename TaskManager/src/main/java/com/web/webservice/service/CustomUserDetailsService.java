package com.web.webservice.service;

import com.web.webservice.api.UserAPI;
import com.web.webservice.dto.UserDto;
import com.web.webservice.model.CustomUserDetails;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAPI userApi;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userApi.findByUsername(username);
        System.out.println(user.toString());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getRole()));
        return new CustomUserDetails(user, grantedAuthoritySet);
    }
}